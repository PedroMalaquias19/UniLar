package com.pedro.UniLar.condominio.mandato;

import com.pedro.UniLar.condominio.mandato.dto.MandatoRequest;
import com.pedro.UniLar.condominio.mandato.dto.MandatoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/condominios/{condominioId}/mandatos")
public class MandatoController {

    private final MandatoService service;

    @PostMapping
    public ResponseEntity<MandatoResponse> criar(@RequestBody MandatoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<MandatoResponse>> listar(@PathVariable Long condominioId) {
        return ResponseEntity.ok(service.listarPorCondominio(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MandatoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MandatoResponse> atualizar(@PathVariable Long id, @RequestBody MandatoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PostMapping("/{id}/encerrar")
    public ResponseEntity<MandatoResponse> encerrar(@PathVariable Long id) {
        return ResponseEntity.ok(service.encerrar(id));
    }

    @PostMapping("/{id}/revogar")
    public ResponseEntity<MandatoResponse> revogar(@PathVariable Long id) {
        return ResponseEntity.ok(service.revogar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
