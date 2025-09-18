package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.moradia.dto.MoradiaRequest;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blocos/{blocoId}/moradias")
public class MoradiaController {

    private final MoradiaService service;

    @PostMapping
    public ResponseEntity<MoradiaResponse> create(@PathVariable Long blocoId,
            @Valid @RequestBody MoradiaRequest request) {
        MoradiaResponse response = service.create(blocoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MoradiaResponse>> list(@PathVariable Long blocoId) {
        return ResponseEntity.ok(service.listByBloco(blocoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoradiaResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoradiaResponse> update(@PathVariable Long id, @Valid @RequestBody MoradiaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}