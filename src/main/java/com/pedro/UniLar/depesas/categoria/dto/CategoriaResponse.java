package com.pedro.UniLar.depesas.categoria.dto;

public record CategoriaResponse(
        Long idCategoria,
        String nome,
        String descricao,
        Long condominioId) {
}
