package com.pedro.UniLar.depesas.categoria;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.depesas.categoria.dto.CategoriaRequest;
import com.pedro.UniLar.depesas.categoria.dto.CategoriaResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequest req, Condominio condominio) {
        if (req == null)
            return null;
        return Categoria.builder()
                .nome(req.nome())
                .descricao(req.descricao())
                .condominio(condominio)
                .build();
    }

    public void update(Categoria entity, CategoriaRequest req, Condominio condominio) {
        if (entity == null || req == null)
            return;
        entity.setNome(req.nome());
        entity.setDescricao(req.descricao());
        entity.setCondominio(condominio);
    }

    public CategoriaResponse toResponse(Categoria entity) {
        if (entity == null)
            return null;
        return new CategoriaResponse(
                entity.getIdCategoria(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getCondominio() != null ? entity.getCondominio().getIdCondominio() : null);
    }
}
