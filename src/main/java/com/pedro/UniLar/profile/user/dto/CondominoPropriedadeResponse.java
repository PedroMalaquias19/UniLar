package com.pedro.UniLar.profile.user.dto;

import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeResponse;
import com.pedro.UniLar.pagamento.dto.PagamentoResponse;

import java.util.List;

// Representa uma propriedade (moradia) pertencente a um condómino, com o contrato associado e os pagamentos.
public record CondominoPropriedadeResponse(
        MoradiaResponse moradia,
        ContratoPropriedadeResponse contrato,
        List<PagamentoResponse> pagamentos) {
}
