package com.pedro.UniLar.condominio.condominio;

import com.pedro.UniLar.condominio.condominio.dto.CondominioRequest;
import com.pedro.UniLar.condominio.condominio.dto.CondominioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/condominios")
@RequiredArgsConstructor
public class CondominioController {

    private final CondominioService service;

    @PostMapping
    public ResponseEntity<CondominioResponse> create(@RequestBody CondominioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CondominioResponse>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondominioResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondominioResponse> update(@PathVariable Long id, @RequestBody CondominioRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    // Não é possível apagar condomínios
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
