package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.moradia.dto.MoradiaRequest;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import com.pedro.UniLar.condominio.bloco.Bloco;
import com.pedro.UniLar.condominio.bloco.BlocoService;
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
public class MoradiaService {

    private final MoradiaRepository repository;
    private final MoradiaMapper mapper;
    private final BlocoService blocoService;
    private final MandatoRepository mandatoRepository;

    @Transactional
    public MoradiaResponse create(Long condominioId, MoradiaRequest request) {
        Bloco bloco = blocoService.getEntity(request.blocoId());
        if (!bloco.getCondominio().getIdCondominio().equals(condominioId)) {
            throw new NotAllowedException("Bloco informado não pertence ao condomínio enviado no path");
        }
        // Authorization: ADMIN or active SINDICO for the condomínio of the bloco can
        // create
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new NotAllowedException("Autenticação necessária");
        }
        if (user.getRole() == Role.ADMIN) {
            // Admin can create directly
        } else if (user.getRole() == Role.SINDICO) {
            Sindico sindico = (Sindico) user;
            var mandatoOpt = mandatoRepository.findMandatoAtivo(condominioId);
            if (mandatoOpt.isEmpty() || !mandatoOpt.get().getSindico().getIdUsuario().equals(sindico.getIdUsuario())) {
                throw new NotAllowedException(
                        "Apenas o síndico com mandato ativo pode criar moradias neste condomínio");
            }
        } else {
            throw new NotAllowedException(
                    "Apenas administradores ou síndicos com mandato ativo podem criar moradias neste condomínio");
        }
        Moradia moradia = mapper.toEntity(request, bloco);
        Moradia saved = repository.save(moradia);
        bloco.adicionarMoradia(saved);
        return mapper.toResponse(saved);
    }

    public MoradiaResponse findById(Long condominioId, Long id) {
        Moradia moradia = getEntity(id);
        if (!moradia.getBloco().getCondominio().getIdCondominio().equals(condominioId)) {
            throw new NotFoundException("Moradia não pertence ao condomínio informado");
        }
        return mapper.toResponse(moradia);
    }

    public List<MoradiaResponse> listByCondominio(Long condominioId) {
        var moradias = repository.findByBloco_Condominio_IdCondominio(condominioId);
        return mapper.toResponseList(moradias);
    }

    @Transactional
    public MoradiaResponse update(Long condominioId, Long id, MoradiaRequest request) {
        Moradia moradia = getEntity(id);
        if (!moradia.getBloco().getCondominio().getIdCondominio().equals(condominioId)) {
            throw new NotFoundException("Moradia não pertence ao condomínio informado");
        }
        // Authorization: ADMIN or active SINDICO for the condomínio of the moradia's
        // bloco can update
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new NotAllowedException("Autenticação necessária");
        }
        if (user.getRole() == Role.ADMIN) {
            // Admin can update directly
        } else if (user.getRole() == Role.SINDICO) {
            Sindico sindico = (Sindico) user;
            var mandatoOpt = mandatoRepository.findMandatoAtivo(condominioId);
            if (mandatoOpt.isEmpty() || !mandatoOpt.get().getSindico().getIdUsuario().equals(sindico.getIdUsuario())) {
                throw new NotAllowedException(
                        "Apenas o síndico com mandato ativo pode atualizar moradias deste condomínio");
            }
        } else {
            throw new NotAllowedException(
                    "Apenas administradores ou síndicos com mandato ativo podem atualizar moradias deste condomínio");
        }
        // Se blocoId vier diferente, validar e atualizar bloco e quota
        if (request.blocoId() != null
                && (moradia.getBloco() == null || !moradia.getBloco().getIdBloco().equals(request.blocoId()))) {
            Bloco novoBloco = blocoService.getEntity(request.blocoId());
            if (!novoBloco.getCondominio().getIdCondominio().equals(condominioId)) {
                throw new NotAllowedException("Bloco informado não pertence ao condomínio enviado no path");
            }
            moradia.setBloco(novoBloco);
            moradia.setQuota(novoBloco.getCondominio().getQuota());
        }
        mapper.updateEntity(moradia, request);
        return mapper.toResponse(moradia);
    }

    @Transactional
    public void delete(Long condominioId, Long id) {
        Moradia moradia = getEntity(id);
        if (!moradia.getBloco().getCondominio().getIdCondominio().equals(condominioId)) {
            throw new NotFoundException("Moradia não pertence ao condomínio informado");
        }
        repository.delete(moradia);
    }

    public Moradia getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Moradia não encontrada: " + id));
    }
}