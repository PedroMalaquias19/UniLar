package com.pedro.UniLar.condominio.mandato.dto;

import com.pedro.UniLar.condominio.mandato.enums.StatusContrato;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MandatoResponse(Long id,
        Long sindicoId,
        Long condominioId,
        LocalDate inicioMandato,
        LocalDate fimMandato,
        BigDecimal salario,
        StatusContrato status,
        String contratoUrl) {
}
