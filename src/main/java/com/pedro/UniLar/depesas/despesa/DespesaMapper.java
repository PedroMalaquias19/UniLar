package com.pedro.UniLar.depesas.despesa;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.depesas.categoria.Categoria;
import com.pedro.UniLar.depesas.despesa.dto.DespesaRequest;
import com.pedro.UniLar.depesas.despesa.dto.DespesaResponse;
import org.springframework.stereotype.Component;

@Component
public class DespesaMapper {

    public Despesa toEntity(DespesaRequest req, Categoria categoria, Condominio condominio) {
        if (req == null)
            return null;
        return Despesa.builder()
                .descricao(req.descricao())
                .categoria(categoria)
                .condominio(condominio)
                .montante(req.montante())
                .dataPagamento(req.dataPagamento())
                .dataVencimento(req.dataVencimento())
                .fornecedor(req.fornecedor())
                .build();
    }

    public void update(Despesa entity, DespesaRequest req, Categoria categoria, Condominio condominio) {
        if (entity == null || req == null)
            return;
        entity.setDescricao(req.descricao());
        entity.setCategoria(categoria);
        entity.setCondominio(condominio);
        entity.setMontante(req.montante());
        entity.setDataPagamento(req.dataPagamento());
        entity.setDataVencimento(req.dataVencimento());
        entity.setFornecedor(req.fornecedor());
    }

    public DespesaResponse toResponse(Despesa entity) {
        if (entity == null)
            return null;
        return new DespesaResponse(
                entity.getIdDespesa(),
                entity.getDescricao(),
                entity.getCategoria() != null ? entity.getCategoria().getIdCategoria() : null,
                entity.getCondominio() != null ? entity.getCondominio().getIdCondominio() : null,
                entity.getMontante(),
                entity.getDataPagamento(),
                entity.getDataVencimento(),
                entity.getEstado(),
                entity.getFornecedor(),
                entity.getFactura());
    }
}
