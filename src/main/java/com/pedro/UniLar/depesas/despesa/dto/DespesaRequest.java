package com.pedro.UniLar.depesas.despesa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaRequest(
        String descricao,
        Long categoriaId,
        Long condominioId,
        BigDecimal montante,
        LocalDate dataPagamento,
        LocalDate dataVencimento,
        String fornecedor) {
}
