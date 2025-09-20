package com.pedro.UniLar.condominio.moradia.dto;

import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeResponse;
import com.pedro.UniLar.profile.user.dto.CondominoResponse;
import java.util.List;

public record MoradiaDetalheResponse(
        MoradiaResponse moradia,
        ContratoPropriedadeResponse contratoAtivo,
        CondominoResponse proprietario,
        List<CondominoResponse> dependentes) {
}
