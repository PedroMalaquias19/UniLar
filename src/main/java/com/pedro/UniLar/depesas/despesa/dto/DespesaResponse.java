package com.pedro.UniLar.depesas.despesa.dto;

import com.pedro.UniLar.pagamento.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaResponse(
        Long idDespesa,
        String descricao,
        Long categoriaId,
        Long condominioId,
        BigDecimal montante,
        LocalDate dataPagamento,
        LocalDate dataVencimento,
        StatusPagamento estado,
        String fornecedor,
        String factura) {
}
