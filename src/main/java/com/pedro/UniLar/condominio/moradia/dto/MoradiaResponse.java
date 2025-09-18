package com.pedro.UniLar.condominio.moradia.dto;

import com.pedro.UniLar.condominio.moradia.enums.TipoMoradia;
import java.math.BigDecimal;

public record MoradiaResponse(Long id, Integer numero, Double area, TipoMoradia tipo, String tipologia,
        BigDecimal quota, Long blocoId, Long condominioId, String condominioNome) {
}
