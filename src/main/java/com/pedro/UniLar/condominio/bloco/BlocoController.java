package com.pedro.UniLar.condominio.bloco;

import com.pedro.UniLar.condominio.bloco.dto.BlocoRequest;
import com.pedro.UniLar.condominio.bloco.dto.BlocoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/condominios/{condominioId}/blocos")
public class BlocoController {

    private final BlocoService service;

    @PostMapping
    public ResponseEntity<BlocoResponse> create(@PathVariable Long condominioId, @RequestBody BlocoRequest request) {
        BlocoResponse response = service.create(condominioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BlocoResponse>> list(@PathVariable Long condominioId) {
        return ResponseEntity.ok(service.listByCondominio(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlocoResponse> find(@PathVariable Long condominioId, @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(condominioId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlocoResponse> update(@PathVariable Long condominioId, @PathVariable Long id,
            @RequestBody BlocoRequest request) {
        return ResponseEntity.ok(service.update(condominioId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long condominioId, @PathVariable Long id) {
        service.delete(condominioId, id);
        return ResponseEntity.noContent().build();
    }
}