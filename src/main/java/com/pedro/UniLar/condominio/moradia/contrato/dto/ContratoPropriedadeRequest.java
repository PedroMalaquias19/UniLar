package com.pedro.UniLar.condominio.moradia.contrato.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record ContratoPropriedadeRequest(
    @Positive Long proprietarioId,
    @NotNull LocalDate inicio,
    LocalDate fim,
    String contratoUrl) {
}
