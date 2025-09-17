package com.pedro.UniLar.condominio.moradia.contrato.dto;

import java.time.LocalDate;

public record ContratoPropriedadeRequest(Long proprietarioId,
                                          LocalDate inicio,
                                          LocalDate fim,
                                          String contratoUrl) {
}
