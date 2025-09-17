package com.pedro.UniLar.condominio.moradia.morador.dto;

public record MoradorResponse(Long id,
                               Long contratoId,
                               Long usuarioId,
                               boolean proprietario) {
}
