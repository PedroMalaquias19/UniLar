package com.pedro.UniLar.condominio.bloco;

import com.pedro.UniLar.condominio.bloco.dto.BlocoRequest;
import com.pedro.UniLar.condominio.bloco.dto.BlocoResponse;
import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.condominio.condominio.CondominioService;
import com.pedro.UniLar.exception.NotFoundException;
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

    @Transactional
    public BlocoResponse create(Long condominioId, BlocoRequest request) {
        Condominio condominio = condominioService.getEntity(condominioId);
        Bloco bloco = mapper.toEntity(request);
        bloco.setCondominio(condominio);

        // numMoradias agora é apenas informativo inicial; moradias serão adicionadas
        // depois.
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

    public BlocoResponse findById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Transactional
    public BlocoResponse update(Long id, BlocoRequest request) {
        Bloco bloco = getEntity(id);
        mapper.updateEntity(bloco, request);
        return mapper.toResponse(bloco);
    }

    @Transactional
    public void delete(Long id) {
        Bloco bloco = getEntity(id);
        blocoRepository.delete(bloco);
    }

    public Bloco getEntity(Long id) {
        return blocoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bloco não encontrado: " + id));
    }
}