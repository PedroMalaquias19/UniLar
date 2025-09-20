package com.pedro.UniLar.depesas.despesa;

import com.pedro.UniLar.depesas.despesa.dto.DespesaRequest;
import com.pedro.UniLar.depesas.despesa.dto.DespesaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/despesas")
@RequiredArgsConstructor
public class DespesaController {

    private final DespesaService service;

    @PostMapping
    public ResponseEntity<DespesaResponse> create(@RequestBody DespesaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/condominio/{condominioId}")
    public ResponseEntity<List<DespesaResponse>> listByCondominio(@PathVariable Long condominioId) {
        return ResponseEntity.ok(service.listByCondominio(condominioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaResponse> update(@PathVariable Long id, @RequestBody DespesaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/fatura", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DespesaResponse> anexarFatura(@PathVariable Long id,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(service.anexarFatura(id, file));
    }

    @PostMapping("/{id}/atrasada")
    public ResponseEntity<DespesaResponse> marcarComoAtrasada(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarComoAtrasada(id));
    }
}
