package com.pedro.UniLar.pagamento;

import com.pedro.UniLar.pagamento.dto.PagamentoRequest;
import com.pedro.UniLar.pagamento.dto.PagamentoResponse;

public class PagamentoMapper {

    public static Pagamento toEntity(PagamentoRequest req) {
        if (req == null)
            return null;
        return Pagamento.builder()
                .montante(req.montante())
                .dataCobranca(req.dataCobranca())
                .vencimento(req.vencimento())
                .build();
    }

    public static PagamentoResponse toResponse(Pagamento p) {
        if (p == null)
            return null;
        return new PagamentoResponse(
                p.getIdPagamento(),
                p.getMoradia() != null ? p.getMoradia().getIdMoradia() : null,
                p.getMontante(),
                p.getComprovativo(),
                p.getStatusPagamento(),
                p.getVencimento(),
                p.getTipo(),
                p.getDataPagamento(),
                p.getDataCobranca());
    }
}
