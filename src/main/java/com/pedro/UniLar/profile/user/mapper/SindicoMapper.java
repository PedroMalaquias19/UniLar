package com.pedro.UniLar.profile.user.mapper;

import com.pedro.UniLar.profile.user.dto.SindicoResponse;
import com.pedro.UniLar.profile.user.entities.Sindico;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SindicoMapper {

    public SindicoResponse toResponse(Sindico e) {
        if (e == null)
            return null;
        return new SindicoResponse(
                e.getIdUsuario(),
                e.getNome(),
                e.getSobrenome(),
                e.getEmail(),
                e.getTelefone(),
                e.getNIF(),
                e.getFotografia().orElse(null));
    }

    public List<SindicoResponse> toResponseList(List<Sindico> list) {
        return list.stream().map(this::toResponse).toList();
    }
}
