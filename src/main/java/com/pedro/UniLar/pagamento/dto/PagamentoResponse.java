package com.pedro.UniLar.pagamento.dto;

import com.pedro.UniLar.pagamento.enums.StatusPagamento;
import com.pedro.UniLar.pagamento.enums.TipoPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponse(
        Long id,
        Long moradiaId,
        BigDecimal montante,
        String comprovativo,
        StatusPagamento status,
        LocalDate vencimento,
        TipoPagamento tipo,
        LocalDate dataPagamento,
        LocalDate dataCobranca) {
}
