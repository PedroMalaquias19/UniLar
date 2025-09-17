package com.pedro.UniLar.condominio.condominio;

import com.pedro.UniLar.condominio.condominio.dto.CondominioRequest;
import com.pedro.UniLar.condominio.condominio.dto.CondominioResponse;
import com.pedro.UniLar.condominio.condominio.dto.CondominioResponse.BlocoResumo;
import com.pedro.UniLar.condominio.bloco.Bloco;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CondominioMapper {

    public Condominio toEntity(CondominioRequest request) {
        if (request == null)
            return null;
        return Condominio.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .quota(request.quota())
                .juros(request.juros())
                .multaFixa(request.multaFixa())
                .toleranciaDias(request.toleranciaDias())
                .build();
    }

    public void updateEntity(Condominio entity, CondominioRequest request) {
        if (entity == null || request == null)
            return;
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setQuota(request.quota());
        entity.setJuros(request.juros());
        entity.setMultaFixa(request.multaFixa());
        entity.setToleranciaDias(request.toleranciaDias());
    }

    public CondominioResponse toResponse(Condominio entity) {
        if (entity == null)
            return null;
        List<BlocoResumo> blocos = entity.getBlocos().stream()
                .map(this::toBlocoResumo)
                .collect(Collectors.toList());
        return new CondominioResponse(
                entity.getIdCondominio(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getQuantBlocos(),
                entity.getQuota(),
                entity.getJuros(),
                entity.getMultaFixa(),
                entity.getToleranciaDias(),
                blocos);
    }

    public List<CondominioResponse> toResponseList(List<Condominio> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private BlocoResumo toBlocoResumo(Bloco bloco) {
        return new BlocoResumo(bloco.getIdBloco(), bloco.getNomeBloco(), bloco.getNumMoradias());
    }
}
