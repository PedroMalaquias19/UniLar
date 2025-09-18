package com.pedro.UniLar.condominio.moradia.dto;

import com.pedro.UniLar.condominio.moradia.enums.TipoMoradia;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

// 'tipo' é obrigatório; 'tipologia' (T1, T2...) é opcional e distinta de 'tipo'
public record MoradiaRequest(Integer numero, Double area, @NotNull TipoMoradia tipo, String tipologia,
        BigDecimal quota) {
}
