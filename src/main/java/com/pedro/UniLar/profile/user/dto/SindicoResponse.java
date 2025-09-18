package com.pedro.UniLar.profile.user.dto;

public record SindicoResponse(
                Long id,
                String nome,
                String sobrenome,
                String email,
                String telefone,
                String nif,
                String fotografia) {
}
