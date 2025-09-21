package com.pedro.UniLar.condominio.mandato.dto;

import java.math.BigDecimal;

/**
 * Informações básicas do condomínio para serem embutidas em outras respostas.
 */
public record CondominioBasicoResponse(
        Long id,
        String nome,
        String descricao,
        Integer quantBlocos,
        BigDecimal quota,
        BigDecimal juros,
        BigDecimal multaFixa,
        Integer toleranciaDias,
        Integer diaCobranca) {
}
