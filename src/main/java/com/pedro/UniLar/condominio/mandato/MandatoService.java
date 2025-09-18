package com.pedro.UniLar.condominio.mandato;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.condominio.condominio.CondominioService;
import com.pedro.UniLar.condominio.mandato.dto.MandatoRequest;
import com.pedro.UniLar.condominio.mandato.dto.MandatoResponse;
import com.pedro.UniLar.condominio.mandato.enums.StatusContrato;
import com.pedro.UniLar.exception.BadRequestException;
import com.pedro.UniLar.exception.NotAllowedException;
import com.pedro.UniLar.profile.user.entities.User;
import com.pedro.UniLar.profile.user.enums.Role;
import com.pedro.UniLar.exception.NotFoundException;
import com.pedro.UniLar.profile.user.entities.Sindico;
import com.pedro.UniLar.profile.user.repositories.SindicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MandatoService {

    private final MandatoRepository repository;
    private final SindicoRepository sindicoRepository;
    private final CondominioService condominioService;
    private final MandatoMapper mapper;

    @Transactional
    public MandatoResponse create(MandatoRequest request) {
        Condominio condominio = condominioService.getEntity(request.condominioId());
        Sindico sindico = sindicoRepository.findById(request.sindicoId())
                .orElseThrow(() -> new NotFoundException("Síndico não encontrado: " + request.sindicoId()));

        validarDatas(request.inicioMandato(), request.fimMandato());
        validarMandatoAtivo(condominio.getIdCondominio());

        Mandato mandato = mapper.toEntity(request);
        mandato.setCondominio(condominio);
        mandato.setSindico(sindico);
        mandato.setStatusContrato(StatusContrato.ATIVO);

        Mandato salvo = repository.save(mandato);
        sindico.adicionarMandato(salvo);
        condominio.adicionarMandato(salvo);
        return mapper.toResponse(salvo);
    }

    public MandatoResponse findById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    public List<MandatoResponse> listarPorCondominio(Long condominioId) {
        Condominio condominio = condominioService.getEntity(condominioId);
        return condominio.getMandatos().stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public MandatoResponse encerrar(Long id) {
        // Apenas ADMIN pode encerrar mandato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)
                || user.getRole() != Role.ADMIN) {
            throw new NotAllowedException("Apenas administradores podem encerrar mandato");
        }
        Mandato mandato = getEntity(id);
        if (mandato.getStatusContrato() != StatusContrato.ATIVO) {
            throw new NotAllowedException("Mandato não está ativo");
        }
        mandato.setStatusContrato(StatusContrato.ENCERRADO);
        mandato.setFimMandato(LocalDate.now());
        return mapper.toResponse(mandato);
    }

    @Transactional
    public MandatoResponse revogar(Long id) {
        // Apenas ADMIN pode revogar contrato ativo de um síndico
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)
                || user.getRole() != Role.ADMIN) {
            throw new NotAllowedException("Apenas administradores podem revogar mandato");
        }
        Mandato mandato = getEntity(id);
        if (mandato.getStatusContrato() != StatusContrato.ATIVO) {
            throw new NotAllowedException("Só é possível revogar contratos ativos");
        }
        mandato.setStatusContrato(StatusContrato.RESCINDIDO);
        mandato.setFimMandato(LocalDate.now());
        return mapper.toResponse(mandato);
    }

    @Transactional
    public MandatoResponse atualizar(Long id, MandatoRequest request) {
        Mandato mandato = getEntity(id);
        mapper.update(mandato, request);
        validarDatas(mandato.getInicioMandato(), mandato.getFimMandato());
        return mapper.toResponse(mandato);
    }

    @Transactional
    public void deletar(Long id) {
        Mandato mandato = getEntity(id);
        repository.delete(mandato);
    }

    private void validarDatas(LocalDate inicio, LocalDate fim) {
        if (inicio == null) {
            throw new BadRequestException("Data de início é obrigatória");
        }
        if (fim != null && fim.isBefore(inicio)) {
            throw new BadRequestException("Data de fim não pode ser antes do início");
        }
    }

    private void validarMandatoAtivo(Long condominioId) {
        if (repository.existsByCondominio_IdCondominioAndStatusContrato(condominioId, StatusContrato.ATIVO)) {
            throw new NotAllowedException("Já existe um mandato ativo neste condomínio");
        }
    }

    protected Mandato getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mandato não encontrado: " + id));
    }
}
