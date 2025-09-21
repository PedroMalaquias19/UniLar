package com.pedro.UniLar.condominio.moradia.contrato;

import com.pedro.UniLar.condominio.moradia.Moradia;
import com.pedro.UniLar.condominio.moradia.MoradiaService;
import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeRequest;
import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeResponse;
import com.pedro.UniLar.condominio.moradia.contrato.enums.StatusContratoPropriedade;
import com.pedro.UniLar.exception.BadRequestException;
import com.pedro.UniLar.exception.NotAllowedException;
import com.pedro.UniLar.exception.NotFoundException;
import com.pedro.UniLar.profile.user.repositories.CondominoRepository;
import com.pedro.UniLar.profile.user.entities.Condomino;
import com.pedro.UniLar.profile.user.entities.Sindico;
import com.pedro.UniLar.profile.user.entities.User;
import com.pedro.UniLar.profile.user.enums.Role;
import com.pedro.UniLar.condominio.mandato.MandatoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContratoPropriedadeService {

    private final ContratoPropriedadeRepository repository;
    private final MoradiaService moradiaService;
    private final CondominoRepository condominoRepository;
    private final ContratoPropriedadeMapper mapper;
    private final MandatoRepository mandatoRepository;

    @Transactional
    public ContratoPropriedadeResponse criar(Long moradiaId, ContratoPropriedadeRequest request) {
        Moradia moradia = moradiaService.getEntity(moradiaId);

        validarDatas(request.inicio(), request.fim());
        validarContratoAtivo(moradia.getIdMoradia());
        Condomino proprietario = condominoRepository.findById(request.proprietarioId())
                .orElseThrow(() -> new NotFoundException(
                        "Condomino proprietário não encontrado: " + request.proprietarioId()));

        ContratoPropriedade contrato = mapper.toEntity(request);
        contrato.setMoradia(moradia);
        contrato.setProprietario(proprietario);

        ContratoPropriedade salvo = repository.save(contrato);
        return mapper.toResponse(salvo);
    }

    public ContratoPropriedadeResponse obterAtivo(Long moradiaId) {
        return repository.findAtivoByMoradia(moradiaId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Nenhum contrato ativo para moradia: " + moradiaId));
    }

    public List<ContratoPropriedadeResponse> historico(Long moradiaId) {
        return repository.findByMoradia_IdMoradiaOrderByInicioDesc(moradiaId).stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public ContratoPropriedadeResponse encerrar(Long contratoId) {
        ContratoPropriedade contrato = getEntity(contratoId);
        if (!contrato.ativo()) {
            throw new NotAllowedException("Contrato não está ativo");
        }
        contrato.setStatus(StatusContratoPropriedade.ENCERRADO);
        contrato.setFim(LocalDate.now());
        return mapper.toResponse(contrato);
    }

    @Transactional
    public ContratoPropriedadeResponse rescindir(Long contratoId) {
        ContratoPropriedade contrato = getEntity(contratoId);
        if (!contrato.ativo()) {
            throw new NotAllowedException("Contrato não está ativo");
        }
        contrato.setStatus(StatusContratoPropriedade.RESCINDIDO);
        contrato.setFim(LocalDate.now());
        return mapper.toResponse(contrato);
    }

    private void autorizarNoCondominioDaMoradia(Moradia moradia) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new NotAllowedException("Autenticação necessária");
        }
        if (user.getRole() == Role.ADMIN)
            return;
        if (user.getRole() == Role.SINDICO) {
            Sindico sindico = (Sindico) user;
            var mandatoOpt = mandatoRepository.findMandatoAtivo(moradia.getBloco().getCondominio().getIdCondominio());
            if (mandatoOpt.isEmpty() || !mandatoOpt.get().getSindico().getIdUsuario().equals(sindico.getIdUsuario())) {
                throw new NotAllowedException("Apenas o síndico com mandato ativo pode executar esta operação");
            }
            return;
        }
        throw new NotAllowedException(
                "Apenas administradores ou síndicos com mandato ativo podem executar esta operação");
    }

    private void validarDatas(LocalDate inicio, LocalDate fim) {
        if (inicio == null) {
            throw new BadRequestException("Data de início obrigatória");
        }
        if (fim != null && fim.isBefore(inicio)) {
            throw new BadRequestException("Data de fim anterior à data de início");
        }
    }

    private void validarContratoAtivo(Long moradiaId) {
        if (repository.existsByMoradia_IdMoradiaAndStatus(moradiaId, StatusContratoPropriedade.ATIVO)) {
            throw new NotAllowedException("Já existe contrato ativo para moradia");
        }
    }

    public ContratoPropriedade getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrato não encontrado: " + id));
    }
}
