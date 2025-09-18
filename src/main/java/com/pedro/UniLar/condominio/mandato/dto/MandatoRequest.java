package com.pedro.UniLar.condominio.mandato.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MandatoRequest(
        Long sindicoId,
        Long condominioId,
        LocalDate inicioMandato,
        LocalDate fimMandato,
        BigDecimal salario,
        String contratoUrl
) {
}
