package com.pedro.UniLar.security.auth.dto;

import java.time.LocalDate;

public record SindicoRegisterRequest(
        String nome,
        String sobrenome,
        String email,
        String password,
        String NIF,
        String telefone,
        LocalDate inicioMandato,
        LocalDate fimMandato,
        String contrato) {
}
