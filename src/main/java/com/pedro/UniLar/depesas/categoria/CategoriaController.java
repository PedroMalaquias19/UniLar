package com.pedro.UniLar.depesas.categoria;

import com.pedro.UniLar.depesas.categoria.dto.CategoriaRequest;
import com.pedro.UniLar.depesas.categoria.dto.CategoriaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/globais")
    public ResponseEntity<List<CategoriaResponse>> listGlobais() {
        return ResponseEntity.ok(service.listGlobais());
    }

    @GetMapping("/condominio/{condominioId}")
    public ResponseEntity<List<CategoriaResponse>> listByCondominio(@PathVariable Long condominioId) {
        // Retorna categorias globais + específicas deste condomínio
        return ResponseEntity.ok(service.listAllForCondominio(condominioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> update(@PathVariable Long id, @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
