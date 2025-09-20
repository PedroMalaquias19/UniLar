package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.bloco.Bloco;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaRequest;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoradiaMapper {

    public Moradia toEntity(MoradiaRequest request, Bloco bloco) {
        if (request == null)
            return null;

        Moradia entity = Moradia.builder()
                .numero(request.numero())
                .area(request.area())
                .tipo(request.tipo())
                .quota(bloco.getCondominio().getQuota())
                .tipologia(request.tipologia())
                .bloco(bloco)
                .build();
        return entity;
    }

    public void updateEntity(Moradia moradia, MoradiaRequest request) {
        moradia.setNumero(request.numero());
        moradia.setArea(request.area());
        moradia.setTipo(request.tipo());
        if (request.tipologia() != null)
            moradia.setTipologia(request.tipologia());
        // Nota: mudança de bloco (e quota associada) deve ser tratada no service
    }

    public MoradiaResponse toResponse(Moradia moradia) {
        if (moradia == null)
            return null;
        return new MoradiaResponse(
                moradia.getIdMoradia(),
                moradia.getNumero(),
                moradia.getArea(),
                moradia.getTipo(),
                moradia.getTipologia(),
                moradia.getBloco().getCondominio().getQuota(),
                moradia.getBloco().getIdBloco(),
                moradia.getBloco().getNomeBloco(),
                moradia.getBloco().getCondominio().getIdCondominio(),
                moradia.getBloco().getCondominio().getNome());
    }


    public List<MoradiaResponse> toResponseList(List<Moradia> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }
}