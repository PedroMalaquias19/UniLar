package com.pedro.UniLar.profile.user.controllers;

import com.pedro.UniLar.profile.user.dto.SindicoResponse;
import com.pedro.UniLar.profile.user.services.SindicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sindicos")
@RequiredArgsConstructor
public class SindicoController {

    private final SindicoService sindicoService;

    @GetMapping
    public ResponseEntity<List<SindicoResponse>> listarTodos() {
        return ResponseEntity.ok(sindicoService.listarTodos());
    }

    @GetMapping("/sem-mandato-ativo")
    public ResponseEntity<List<SindicoResponse>> listarSemMandatoAtivo() {
        return ResponseEntity.ok(sindicoService.listarSemMandatoAtivo());
    }
}
