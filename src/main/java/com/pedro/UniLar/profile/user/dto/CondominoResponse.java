package com.pedro.UniLar.profile.user.dto;

import com.pedro.UniLar.profile.user.enums.TipoCondomino;

public record CondominoResponse(
                Long id,
                String nome,
                String sobrenome,
                String email,
                String telefone,
                String nif,
                String fotografia,
                TipoCondomino tipo) {
}
