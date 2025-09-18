package com.pedro.UniLar.condominio.bloco.dto;

import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import java.util.List;

public record BlocoResponse(
        Long id,
        String nomeBloco,
        Integer numMoradias,
        Long condominioId,
        String condominioNome,
        List<MoradiaResponse> moradias) {
}
