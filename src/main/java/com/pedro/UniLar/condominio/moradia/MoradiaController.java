package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.moradia.dto.MoradiaRequest;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaResponse;
import com.pedro.UniLar.condominio.moradia.dto.MoradiaDetalheResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/condominios/{condominioId}/moradias")
public class MoradiaController {

    private final MoradiaService service;

    @PostMapping
    public ResponseEntity<MoradiaResponse> create(@PathVariable Long condominioId,
            @Valid @RequestBody MoradiaRequest request) {
        MoradiaResponse response = service.create(condominioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MoradiaResponse>> list(@PathVariable Long condominioId) {
        return ResponseEntity.ok(service.listByCondominio(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoradiaResponse> find(@PathVariable Long condominioId, @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(condominioId, id));
    }

    @GetMapping("/{id}/detalhes")
    public ResponseEntity<MoradiaDetalheResponse> detalhes(@PathVariable Long condominioId, @PathVariable Long id) {
        return ResponseEntity.ok(service.detalhes(condominioId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoradiaResponse> update(@PathVariable Long condominioId, @PathVariable Long id,
            @Valid @RequestBody MoradiaRequest request) {
        return ResponseEntity.ok(service.update(condominioId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long condominioId, @PathVariable Long id) {
        service.delete(condominioId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{moradiaId}/moradores/{condominoId}")
    public ResponseEntity<MoradiaResponse> adicionarMorador(@PathVariable Long condominioId,
            @PathVariable Long moradiaId, @PathVariable Long condominoId) {
        return ResponseEntity.ok(service.adicionarMorador(condominioId, moradiaId, condominoId));
    }

    @DeleteMapping("/{moradiaId}/moradores/{condominoId}")
    public ResponseEntity<Void> removerMorador(@PathVariable Long condominioId, @PathVariable Long moradiaId,
            @PathVariable Long condominoId) {
        service.removerMorador(condominioId, moradiaId, condominoId);
        return ResponseEntity.noContent().build();
    }
}