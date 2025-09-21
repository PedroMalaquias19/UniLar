package com.pedro.UniLar.condominio.mandato.dto;

import com.pedro.UniLar.condominio.mandato.enums.StatusContrato;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Resposta de Mandato contendo também as informações básicas do condomínio.
 */
public record MandatoComCondominioResponse(
        Long id,
        Long sindicoId,
        CondominioBasicoResponse condominio,
        LocalDate inicioMandato,
        LocalDate fimMandato,
        BigDecimal salario,
        StatusContrato status,
        String contratoUrl) {
}
