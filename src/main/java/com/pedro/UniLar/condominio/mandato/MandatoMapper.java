package com.pedro.UniLar.condominio.mandato;

import com.pedro.UniLar.condominio.mandato.dto.MandatoRequest;
import com.pedro.UniLar.condominio.mandato.dto.MandatoResponse;
import org.springframework.stereotype.Component;

@Component
public class MandatoMapper {

    public Mandato toEntity(MandatoRequest request) {
        if (request == null)
            return null;
        return Mandato.builder()
                .inicioMandato(request.inicioMandato())
                .fimMandato(request.fimMandato())
                .salario(request.salario())
                .contratoUrl(request.contratoUrl())
                .build();
    }

    public void update(Mandato entity, MandatoRequest request) {
        if (request == null)
            return;
        if (request.inicioMandato() != null)
            entity.setInicioMandato(request.inicioMandato());
        entity.setFimMandato(request.fimMandato());
        entity.setSalario(request.salario());
        entity.setContratoUrl(request.contratoUrl());
    }

    public MandatoResponse toResponse(Mandato entity) {
        if (entity == null)
            return null;
        return new MandatoResponse(
                entity.getIdMandato(),
                entity.getSindico() != null ? entity.getSindico().getIdUsuario() : null,
                entity.getCondominio() != null ? entity.getCondominio().getIdCondominio() : null,
                entity.getInicioMandato(),
                entity.getFimMandato(),
                entity.getSalario(),
                entity.getStatusContrato(),
                entity.getContratoUrl());
    }
}
