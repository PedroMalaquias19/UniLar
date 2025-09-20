package com.pedro.UniLar.depesas.categoria.dto;

public record CategoriaRequest(
        String nome,
        String descricao,
        Long condominioId // null => global (somente ADMIN)
) {
}
