package com.pedro.UniLar.condominio.bloco;

import com.pedro.UniLar.condominio.bloco.dto.BlocoRequest;
import com.pedro.UniLar.condominio.bloco.dto.BlocoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlocoMapper {

    public Bloco toEntity(BlocoRequest request) {
        if (request == null)
            return null;
        Bloco bloco = Bloco.builder()
                .nomeBloco(request.nomeBloco())
                .build();
        if (request.numMoradias() != null) {
            bloco.setNumMoradias(request.numMoradias());
        }
        return bloco;
    }

    public BlocoResponse toResponse(Bloco bloco) {
        if (bloco == null)
            return null;
        return new BlocoResponse(
                bloco.getIdBloco(),
                bloco.getNomeBloco(),
                bloco.getNumMoradias());
    }

    public List<BlocoResponse> toResponseList(List<Bloco> blocos) {
        return blocos.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public void updateEntity(Bloco bloco, BlocoRequest request) {
        bloco.setNomeBloco(request.nomeBloco());
        if (request.numMoradias() != null) {
            bloco.setNumMoradias(request.numMoradias());
        }
    }

    // Removido attachMoradias: agora moradias serão criadas separadamente via
    // endpoint próprio.
}