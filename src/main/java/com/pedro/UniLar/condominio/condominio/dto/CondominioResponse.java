package com.pedro.UniLar.condominio.condominio.dto;

import java.math.BigDecimal;
import java.util.List;

public record CondominioResponse(
        Long id,
        String nome,
        String descricao,
        Integer quantBlocos,
        BigDecimal quota,
        BigDecimal juros,
        BigDecimal multaFixa,
        Integer toleranciaDias,
        Integer diaCobranca,
        List<BlocoResumo> blocos) {
    public record BlocoResumo(Long id, String nomeBloco, Integer numMoradias) {
    }
}
