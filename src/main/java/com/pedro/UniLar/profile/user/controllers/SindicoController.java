package com.pedro.UniLar.profile.user.controllers;

import com.pedro.UniLar.profile.user.dto.SindicoResponse;
import com.pedro.UniLar.profile.user.services.SindicoService;
import com.pedro.UniLar.condominio.mandato.MandatoService;
import com.pedro.UniLar.condominio.mandato.dto.MandatoComCondominioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sindicos")
@RequiredArgsConstructor
public class SindicoController {

    private final SindicoService sindicoService;
    private final MandatoService mandatoService;

    @GetMapping
    public ResponseEntity<List<SindicoResponse>> listarTodos() {
        return ResponseEntity.ok(sindicoService.listarTodos());
    }

    @GetMapping("/sem-mandato-ativo")
    public ResponseEntity<List<SindicoResponse>> listarSemMandatoAtivo() {
        return ResponseEntity.ok(sindicoService.listarSemMandatoAtivo());
    }

    @GetMapping("/{sindicoId}/mandatos")
    public ResponseEntity<List<MandatoComCondominioResponse>> listarMandatosPorSindico(
            @PathVariable Long sindicoId) {
        return ResponseEntity.ok(mandatoService.listarPorSindico(sindicoId));
    }
}
