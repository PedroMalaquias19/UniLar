package com.pedro.UniLar.condominio.bloco;

import com.pedro.UniLar.condominio.bloco.dto.BlocoRequest;
import com.pedro.UniLar.condominio.bloco.dto.BlocoResponse;
import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.condominio.condominio.CondominioService;
import com.pedro.UniLar.condominio.mandato.MandatoRepository;
import com.pedro.UniLar.exception.NotAllowedException;
import com.pedro.UniLar.exception.NotFoundException;
import com.pedro.UniLar.profile.user.entities.Sindico;
import com.pedro.UniLar.profile.user.entities.User;
import com.pedro.UniLar.profile.user.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlocoService {

    private final BlocoRepository blocoRepository;
    private final CondominioService condominioService;
    private final BlocoMapper mapper;
    private final MandatoRepository mandatoRepository;

    @Transactional
    public BlocoResponse create(Long condominioId, BlocoRequest request) {
        Condominio condominio = condominioService.getEntity(condominioId);
        // Authorization: only active sindico for this condominium can create
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new NotAllowedException("Autenticação necessária");
        }
        if (user.getRole() == Role.SINDICO) {
            Sindico sindico = (Sindico) user;
            var mandatoOpt = mandatoRepository.findMandatoAtivo(condominio.getIdCondominio());
            if (mandatoOpt.isEmpty() || !mandatoOpt.get().getSindico().getIdUsuario().equals(sindico.getIdUsuario())) {
                throw new NotAllowedException("Apenas o síndico com mandato ativo pode criar blocos neste condomínio");
            }
        } else {
            throw new NotAllowedException("Apenas o síndico com mandato ativo pode criar blocos neste condomínio");
        }
        Bloco bloco = mapper.toEntity(request);
        bloco.setCondominio(condominio);

        if (request.numMoradias() != null) {
            bloco.setNumMoradias(request.numMoradias());
        }

        Bloco saved = blocoRepository.save(bloco);
        condominio.adicionarBloco(saved);
        return mapper.toResponse(saved);
    }

    public List<BlocoResponse> listByCondominio(Long condominioId) {
        Condominio condominio = condominioService.getEntity(condominioId);
        return mapper.toResponseList(condominio.getBlocos());
    }

    public BlocoResponse findById(Long condominioId, Long id) {
        Bloco bloco = getEntity(id);
        if (!bloco.getCondominio().getIdCondominio().equals(condominioId)) {
            throw new NotFoundException("Bloco não encontrado no condomínio informado: " + id);
        }
        return mapper.toResponse(bloco);
    }

    @Transactional
    public BlocoResponse update(Long condominioId, Long id, BlocoRequest request) {
        Bloco bloco = getEntity(id);
        if (!bloco.getCondominio().getIdCondominio().equals(condominioId)) {
            throw new NotFoundException("Bloco não encontrado no condomínio informado: " + id);
        }
        // Authorization: only active sindico for this condominium can update
        Condominio condominio = bloco.getCondominio();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new NotAllowedException("Autenticação necessária");
        }
        if (user.getRole() == Role.SINDICO) {
            Sindico sindico = (Sindico) user;
            var mandatoOpt = mandatoRepository.findMandatoAtivo(condominio.getIdCondominio());
            if (mandatoOpt.isEmpty() || !mandatoOpt.get().getSindico().getIdUsuario().equals(sindico.getIdUsuario())) {
                throw new NotAllowedException(
                        "Apenas o síndico com mandato ativo pode atualizar blocos deste condomínio");
            }
        } else {
            throw new NotAllowedException("Apenas o síndico com mandato ativo pode atualizar blocos deste condomínio");
        }
        mapper.updateEntity(bloco, request);
        return mapper.toResponse(bloco);
    }

    @Transactional
    public void delete(Long condominioId, Long id) {
        Bloco bloco = getEntity(id);
        if (!bloco.getCondominio().getIdCondominio().equals(condominioId)) {
            throw new NotFoundException("Bloco não encontrado no condomínio informado: " + id);
        }
        blocoRepository.delete(bloco);
    }

    public Bloco getEntity(Long id) {
        return blocoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bloco não encontrado: " + id));
    }
}