package com.pedro.UniLar.profile.user.mapper;

import com.pedro.UniLar.profile.user.dto.CondominoResponse;
import com.pedro.UniLar.profile.user.entities.Condomino;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CondominoMapper {

    public CondominoResponse toResponse(Condomino e) {
        if (e == null)
            return null;
        return new CondominoResponse(
                e.getIdUsuario(),
                e.getNome(),
                e.getSobrenome(),
                e.getEmail(),
                e.getTelefone(),
                e.getNIF(),
                e.getFotografia().orElse(null),
                e.getTipo());
    }

    public List<CondominoResponse> toResponseList(List<Condomino> list) {
        return list.stream().map(this::toResponse).toList();
    }
}
