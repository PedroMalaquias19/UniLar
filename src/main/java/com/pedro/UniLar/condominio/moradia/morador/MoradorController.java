package com.pedro.UniLar.condominio.moradia.morador;

import com.pedro.UniLar.condominio.moradia.morador.dto.MoradorRequest;
import com.pedro.UniLar.condominio.moradia.morador.dto.MoradorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contratos-propriedade/{contratoId}/moradores")
public class MoradorController {

    private final MoradorService service;

    @PostMapping
    public ResponseEntity<MoradorResponse> adicionar(@PathVariable Long contratoId, @RequestBody MoradorRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionar(contratoId, request));
    }

    @GetMapping
    public ResponseEntity<List<MoradorResponse>> listar(@PathVariable Long contratoId){
        return ResponseEntity.ok(service.listar(contratoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
