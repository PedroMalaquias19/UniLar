package com.pedro.UniLar.profile.user.dto;

import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeResponse;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import java.util.List;

public record UserContratosResponse(
        UserResponse usuario,
        List<ItemMoradiaContrato> moradias) {

    public static record ItemMoradiaContrato(
            MoradiaResponse moradia,
            ContratoPropriedadeResponse contrato) {
    }
}
