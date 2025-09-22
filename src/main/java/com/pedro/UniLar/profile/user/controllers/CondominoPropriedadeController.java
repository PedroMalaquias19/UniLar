package com.pedro.UniLar.profile.user.controllers;

import com.pedro.UniLar.profile.user.dto.CondominoPropriedadeResponse;
import com.pedro.UniLar.profile.user.services.CondominoPropriedadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/condominos/{condominoId}/propriedades")
public class CondominoPropriedadeController {

    private final CondominoPropriedadeService service;

    @GetMapping
    public ResponseEntity<List<CondominoPropriedadeResponse>> listar(@PathVariable Long condominoId) {
        return ResponseEntity.ok(service.listarPropriedadesComContratoEPagamentos(condominoId));
    }
}
