package com.pedro.UniLar.security.auth.dto;

import com.pedro.UniLar.profile.user.enums.TipoCondomino;
import java.time.LocalDate;

public record CondominoRegisterRequest(
        String nome,
        String sobrenome,
        String email,
        String password,
        String NIF,
        String telefone,
        LocalDate dataDeEntrada,
        LocalDate dataDeSaida,
        String contrato,
        TipoCondomino tipo) {
}
