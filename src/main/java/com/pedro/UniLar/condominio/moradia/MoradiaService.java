package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.moradia.dto.MoradiaRequest;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import com.pedro.UniLar.condominio.bloco.Bloco;
import com.pedro.UniLar.condominio.bloco.BlocoService;
import com.pedro.UniLar.exception.NotFoundException;
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

    @Transactional
    public MoradiaResponse create(Long blocoId, MoradiaRequest request) {
        Bloco bloco = blocoService.getEntity(blocoId);
        Moradia moradia = mapper.toEntity(request);
        moradia.setBloco(bloco);
        Moradia saved = repository.save(moradia);
        bloco.adicionarMoradia(saved);
        return mapper.toResponse(saved);
    }

    public MoradiaResponse findById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    public List<MoradiaResponse> listByBloco(Long blocoId) {
        Bloco bloco = blocoService.getEntity(blocoId);
        return mapper.toResponseList(bloco.getMoradias());
    }

    @Transactional
    public MoradiaResponse update(Long id, MoradiaRequest request) {
        Moradia moradia = getEntity(id);
        mapper.updateEntity(moradia, request);
        return mapper.toResponse(moradia);
    }

    @Transactional
    public void delete(Long id) {
        Moradia moradia = getEntity(id);
        repository.delete(moradia);
    }

    public Moradia getEntity(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Moradia não encontrada: " + id));
    }
}