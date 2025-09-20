package com.pedro.UniLar.pagamento.dto;

import java.time.LocalDate;

// Usado para criar pagamentos manuais (opcional). A geração mensal será automática.
public record PagamentoRequest(
        Long moradiaId,
        java.math.BigDecimal montante,
        LocalDate dataCobranca,
        LocalDate vencimento) {
}
