package com.pedro.UniLar.condominio.condominio;

import com.pedro.UniLar.condominio.condominio.dto.CondominioRequest;
import com.pedro.UniLar.condominio.condominio.dto.CondominioResponse;
import com.pedro.UniLar.condominio.condominio.dto.CondominioResponse.BlocoResumo;
import com.pedro.UniLar.condominio.bloco.Bloco;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CondominioMapper {

    public Condominio toEntity(CondominioRequest request) {
        if (request == null)
            return null;
        BigDecimal quota = request.quota();
        BigDecimal multaFixa = request.multaFixa();
        BigDecimal juros = request.juros();
        // Regra: Sempre que receber juros e multa fixa, recalcule juros como multaFixa
        // / quota
        if (quota != null && multaFixa != null && quota.compareTo(BigDecimal.ZERO) != 0) {
            juros = multaFixa.divide(quota, 4, RoundingMode.HALF_UP);
        }
        Integer diaCobranca = request.diaCobranca() != null ? request.diaCobranca() : 1;

        return Condominio.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .quota(quota)
                .juros(juros)
                .multaFixa(multaFixa)
                .toleranciaDias(request.toleranciaDias())
                .diaCobranca(diaCobranca)
                .build();
    }

    public void updateEntity(Condominio entity, CondominioRequest request) {
        if (entity == null || request == null)
            return;
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setQuota(request.quota());
        // Recalcular juros se tiver quota e multaFixa
        if (request.quota() != null && request.multaFixa() != null && request.quota().compareTo(BigDecimal.ZERO) != 0) {
            entity.setJuros(request.multaFixa().divide(request.quota(), 4, RoundingMode.HALF_UP));
        } else if (request.juros() != null) {
            entity.setJuros(request.juros());
        }
        entity.setMultaFixa(request.multaFixa());
        entity.setToleranciaDias(request.toleranciaDias());
        if (request.diaCobranca() != null) {
            entity.setDiaCobranca(request.diaCobranca());
        }
    }

    public CondominioResponse toResponse(Condominio entity) {
        if (entity == null)
            return null;
        List<BlocoResumo> blocos = entity.getBlocos().stream()
                .map(this::toBlocoResumo)
                .collect(Collectors.toList());
        return new CondominioResponse(
                entity.getIdCondominio(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getQuantBlocos(),
                entity.getQuota(),
                entity.getJuros(),
                entity.getMultaFixa(),
                entity.getToleranciaDias(),
                entity.getDiaCobranca(),
                blocos);
    }

    public List<CondominioResponse> toResponseList(List<Condominio> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private BlocoResumo toBlocoResumo(Bloco bloco) {
        return new BlocoResumo(bloco.getIdBloco(), bloco.getNomeBloco(), bloco.getNumMoradias());
    }
}
