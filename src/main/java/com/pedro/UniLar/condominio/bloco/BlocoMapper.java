package com.pedro.UniLar.condominio.bloco;

import com.pedro.UniLar.condominio.bloco.dto.BlocoRequest;
import com.pedro.UniLar.condominio.bloco.dto.BlocoResponse;
import com.pedro.UniLar.condominio.moradia.MoradiaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlocoMapper {

    private final MoradiaMapper moradiaMapper;

    public BlocoMapper(MoradiaMapper moradiaMapper) {
        this.moradiaMapper = moradiaMapper;
    }

    public Bloco toEntity(BlocoRequest request) {
        if (request == null)
            return null;
        Bloco bloco = Bloco.builder()
                .nomeBloco(request.nomeBloco())
                .numMoradias(request.numMoradias())
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
                bloco.getNumMoradias(),
                bloco.getCondominio().getIdCondominio(),
                bloco.getCondominio().getNome(),
                moradiaMapper.toResponseList(bloco.getMoradias()));
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
}