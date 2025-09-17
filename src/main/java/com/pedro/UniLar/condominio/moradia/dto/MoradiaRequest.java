package com.pedro.UniLar.condominio.moradia.dto;

import com.pedro.UniLar.condominio.moradia.enums.TipoMoradia;
import java.math.BigDecimal;

public record MoradiaRequest(Integer numero, Double area, TipoMoradia tipo, BigDecimal quota) {
}
