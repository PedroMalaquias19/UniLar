package com.pedro.UniLar.profile.user.controllers;

import com.pedro.UniLar.profile.user.dto.CondominoResponse;
import com.pedro.UniLar.profile.user.services.CondominoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/condominos")
@RequiredArgsConstructor
public class CondominoController {

    private final CondominoService condominoService;

    @GetMapping
    public ResponseEntity<List<CondominoResponse>> listarTodos() {
        return ResponseEntity.ok(condominoService.listarTodos());
    }

    @GetMapping("/condominios/{condominioId}")
    public ResponseEntity<List<CondominoResponse>> listarPorCondominio(@PathVariable Long condominioId) {
        return ResponseEntity.ok(condominoService.listarPorCondominio(condominioId));
    }

    @GetMapping("/condominios/{condominioId}/proprietarios")
    public ResponseEntity<List<CondominoResponse>> listarProprietariosPorCondominio(@PathVariable Long condominioId) {
        return ResponseEntity.ok(condominoService.listarProprietariosPorCondominio(condominioId));
    }
}
