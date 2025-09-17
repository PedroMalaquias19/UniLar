package com.pedro.UniLar.condominio.moradia.contrato.dto;

import com.pedro.UniLar.condominio.moradia.contrato.enums.StatusContratoPropriedade;
import java.time.LocalDate;

public record ContratoPropriedadeResponse(Long id,
                                          Long moradiaId,
                                          Long proprietarioId,
                                          LocalDate inicio,
                                          LocalDate fim,
                                          StatusContratoPropriedade status,
                                          String contratoUrl) {
}
