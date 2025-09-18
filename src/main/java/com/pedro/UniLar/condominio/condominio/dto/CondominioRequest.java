package com.pedro.UniLar.condominio.condominio.dto;

import java.math.BigDecimal;

public record CondominioRequest(
                String nome,
                String descricao,
                BigDecimal quota,
                BigDecimal juros,
                BigDecimal multaFixa,
                Integer toleranciaDias,
                Integer diaCobranca) {
}
