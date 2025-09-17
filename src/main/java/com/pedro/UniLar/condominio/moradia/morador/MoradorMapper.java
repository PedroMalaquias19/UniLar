package com.pedro.UniLar.condominio.moradia.morador;

import com.pedro.UniLar.condominio.moradia.morador.dto.MoradorRequest;
import com.pedro.UniLar.condominio.moradia.morador.dto.MoradorResponse;
import org.springframework.stereotype.Component;

@Component
public class MoradorMapper {

    public Morador toEntity(MoradorRequest request){
        if(request == null) return null;
        Morador m = new Morador();
        m.setProprietario(Boolean.TRUE.equals(request.proprietario()));
        return m;
    }

    public void update(Morador entity, MoradorRequest request){
        if(request == null) return;
        if(request.proprietario() != null){
            entity.setProprietario(request.proprietario());
        }
    }

    public MoradorResponse toResponse(Morador entity){
        if(entity == null) return null;
        return new MoradorResponse(
                entity.getIdMorador(),
                entity.getContrato() != null ? entity.getContrato().getIdContrato() : null,
                entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null,
                entity.isProprietario()
        );
    }
}
