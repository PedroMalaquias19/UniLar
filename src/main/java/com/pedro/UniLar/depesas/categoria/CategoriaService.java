package com.pedro.UniLar.depesas.categoria;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.condominio.condominio.CondominioService;
import com.pedro.UniLar.condominio.mandato.MandatoRepository;
import com.pedro.UniLar.condominio.mandato.enums.StatusContrato;
import com.pedro.UniLar.depesas.categoria.dto.CategoriaRequest;
import com.pedro.UniLar.depesas.categoria.dto.CategoriaResponse;
import com.pedro.UniLar.exception.NotAllowedException;
import com.pedro.UniLar.exception.NotFoundException;
import com.pedro.UniLar.profile.user.entities.User;
import com.pedro.UniLar.profile.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;
    private final CondominioService condominioService;
    private final MandatoRepository mandatoRepository;

    @Transactional
    public CategoriaResponse create(CategoriaRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new NotAllowedException("Utilizador não autenticado");
        }

        Condominio condominio = null;
        if (req.condominioId() != null) {
            condominio = condominioService.getEntity(req.condominioId());
            // ADMIN sempre pode, SINDICO só se tiver mandato ATIVO neste condomínio
            if (user.getRole() != Role.ADMIN) {
                boolean ativo = mandatoRepository.existsByCondominio_IdCondominioAndStatusContrato(
                        condominio.getIdCondominio(), StatusContrato.ATIVO);
                if (!ativo) {
                    throw new NotAllowedException(
                            "Apenas síndicos com mandato ativo ou administradores podem criar categorias para o condomínio");
                }
            }
        } else {
            // Somente ADMIN pode criar categoria global (sem condomínio)
            if (user.getRole() != Role.ADMIN)
                throw new NotAllowedException("Apenas administradores podem criar categorias globais");
        }

        Categoria entity = mapper.toEntity(req, condominio);
        entity = repository.save(entity);
        return mapper.toResponse(entity);
    }

    public List<CategoriaResponse> listGlobais() {
        return repository.findGlobais().stream().map(mapper::toResponse).toList();
    }

    public List<CategoriaResponse> listByCondominio(Long condominioId) {
        return repository.findByCondominio(condominioId).stream().map(mapper::toResponse).toList();
    }

    public List<CategoriaResponse> listAllForCondominio(Long condominioId) {
        return repository.findGlobaisOuDoCondominio(condominioId).stream().map(mapper::toResponse).toList();
    }

    public Categoria getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada: " + id));
    }

    @Transactional
    public CategoriaResponse update(Long id, CategoriaRequest req) {
        Categoria entity = getEntity(id);
        Condominio condominio = req.condominioId() != null ? condominioService.getEntity(req.condominioId()) : null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new NotAllowedException("Utilizador não autenticado");
        }
        if (condominio == null && user.getRole() != Role.ADMIN)
            throw new NotAllowedException("Apenas administradores podem definir categoria global");
        if (condominio != null && user.getRole() != Role.ADMIN) {
            boolean ativo = mandatoRepository.existsByCondominio_IdCondominioAndStatusContrato(
                    condominio.getIdCondominio(), StatusContrato.ATIVO);
            if (!ativo) {
                throw new NotAllowedException(
                        "Apenas síndicos com mandato ativo ou administradores podem atualizar categorias do condomínio");
            }
        }

        mapper.update(entity, req, condominio);
        return mapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }
}
