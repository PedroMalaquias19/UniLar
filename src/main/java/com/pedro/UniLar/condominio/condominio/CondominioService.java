package com.pedro.UniLar.condominio.condominio;

import com.pedro.UniLar.condominio.condominio.dto.CondominioRequest;
import com.pedro.UniLar.condominio.condominio.dto.CondominioResponse;
import com.pedro.UniLar.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CondominioService {

    private final CondominioRepository repository;
    private final CondominioMapper mapper;

    @Transactional
    public CondominioResponse create(CondominioRequest request) {
        Condominio entity = mapper.toEntity(request);
        Condominio saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    public List<CondominioResponse> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    public CondominioResponse findById(Long id) {
        Condominio entity = getEntity(id);
        return mapper.toResponse(entity);
    }

    @Transactional
    public CondominioResponse update(Long id, CondominioRequest request) {
        Condominio entity = getEntity(id);
        mapper.updateEntity(entity, request);
        return mapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        Condominio entity = getEntity(id);
        repository.delete(entity);
    }

    public Condominio getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Condomínio não encontrado: " + id));
    }
}
