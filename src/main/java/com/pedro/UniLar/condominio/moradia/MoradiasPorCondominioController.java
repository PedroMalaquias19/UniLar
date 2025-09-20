package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/condominios/{condominioId}/moradias")
public class MoradiasPorCondominioController {

    private final MoradiaService service;

    @GetMapping
    public ResponseEntity<List<MoradiaResponse>> list(@PathVariable Long condominioId) {
        return ResponseEntity.ok(service.listByCondominio(condominioId));
    }
}
