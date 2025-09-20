package com.pedro.UniLar.condominio.moradia.dto;

import com.pedro.UniLar.condominio.moradia.enums.TipoMoradia;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MoradiaRequest(
        Integer numero,
        Double area,
        @NotNull TipoMoradia tipo,
        String tipologia,
        @NotNull Long blocoId
) {
}
