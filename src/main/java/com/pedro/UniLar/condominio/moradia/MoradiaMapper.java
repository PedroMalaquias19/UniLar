package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.moradia.dto.MoradiaRequest;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoradiaMapper {

    public Moradia toEntity(MoradiaRequest request) {
        if (request == null)
            return null;
        return Moradia.builder()
                .numero(request.numero())
                .area(request.area())
                .tipo(request.tipo())
                .tipologia(request.tipologia())
                .quota(request.quota())
                .build();
    }

    public void updateEntity(Moradia moradia, MoradiaRequest request) {
        moradia.setNumero(request.numero());
        moradia.setArea(request.area());
        // 'tipo' é obrigatório, então atualizamos sempre
        moradia.setTipo(request.tipo());
        if (request.tipologia() != null)
            moradia.setTipologia(request.tipologia());
        moradia.setQuota(request.quota());
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
                moradia.getQuota(),
                moradia.getBloco().getIdBloco(),
                moradia.getBloco().getCondominio().getIdCondominio(),
                moradia.getBloco().getCondominio().getNome());
    }

    public List<MoradiaResponse> toResponseList(List<Moradia> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }
}