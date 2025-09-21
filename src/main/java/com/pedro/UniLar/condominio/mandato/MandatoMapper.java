package com.pedro.UniLar.condominio.mandato;

import com.pedro.UniLar.condominio.mandato.dto.MandatoRequest;
import com.pedro.UniLar.condominio.mandato.dto.MandatoResponse;
import com.pedro.UniLar.condominio.mandato.dto.MandatoComCondominioResponse;
import com.pedro.UniLar.condominio.mandato.dto.CondominioBasicoResponse;
import com.pedro.UniLar.condominio.condominio.Condominio;
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

    public MandatoComCondominioResponse toResponseComCondominio(Mandato entity) {
        if (entity == null)
            return null;
        CondominioBasicoResponse cond = toCondominioBasico(entity.getCondominio());
        return new MandatoComCondominioResponse(
                entity.getIdMandato(),
                entity.getSindico() != null ? entity.getSindico().getIdUsuario() : null,
                cond,
                entity.getInicioMandato(),
                entity.getFimMandato(),
                entity.getSalario(),
                entity.getStatusContrato(),
                entity.getContratoUrl());
    }

    private CondominioBasicoResponse toCondominioBasico(Condominio condominio) {
        if (condominio == null)
            return null;
        return new CondominioBasicoResponse(
                condominio.getIdCondominio(),
                condominio.getNome(),
                condominio.getDescricao(),
                condominio.getQuantBlocos(),
                condominio.getQuota(),
                condominio.getJuros(),
                condominio.getMultaFixa(),
                condominio.getToleranciaDias(),
                condominio.getDiaCobranca());
    }
}
