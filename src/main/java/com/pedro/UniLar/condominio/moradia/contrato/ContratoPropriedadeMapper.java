package com.pedro.UniLar.condominio.moradia.contrato;

import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeRequest;
import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeResponse;
import com.pedro.UniLar.condominio.moradia.contrato.enums.StatusContratoPropriedade;
import org.springframework.stereotype.Component;

@Component
public class ContratoPropriedadeMapper {

    public ContratoPropriedade toEntity(ContratoPropriedadeRequest request){
        if(request == null) return null;
        return ContratoPropriedade.builder()
                .inicio(request.inicio())
                .fim(request.fim())
                .contratoUrl(request.contratoUrl())
                .status(StatusContratoPropriedade.ATIVO)
                .build();
    }

    public void update(ContratoPropriedade entity, ContratoPropriedadeRequest request){
        if(request == null) return;
        if(request.inicio() != null) entity.setInicio(request.inicio());
        entity.setFim(request.fim());
        entity.setContratoUrl(request.contratoUrl());
    }

    public ContratoPropriedadeResponse toResponse(ContratoPropriedade entity){
        if(entity == null) return null;
        return new ContratoPropriedadeResponse(
                entity.getIdContrato(),
                entity.getMoradia() != null ? entity.getMoradia().getIdMoradia() : null,
                entity.getProprietario() != null ? entity.getProprietario().getIdUsuario() : null,
                entity.getInicio(),
                entity.getFim(),
                entity.getStatus(),
                entity.getContratoUrl()
        );
    }
}
