package com.pedro.UniLar.condominio.moradia.contrato;

import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeRequest;
import com.pedro.UniLar.condominio.moradia.contrato.dto.ContratoPropriedadeResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/moradias/{moradiaId}/contratos-propriedade")
public class ContratoPropriedadeController {

    private final ContratoPropriedadeService service;

    @PostMapping
    public ResponseEntity<ContratoPropriedadeResponse> criar(@PathVariable Long moradiaId,
            @Valid @RequestBody ContratoPropriedadeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(moradiaId, request));
    }

    @GetMapping("/ativo")
    public ResponseEntity<ContratoPropriedadeResponse> obterAtivo(@PathVariable Long moradiaId) {
        return ResponseEntity.ok(service.obterAtivo(moradiaId));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<ContratoPropriedadeResponse>> historico(@PathVariable Long moradiaId) {
        return ResponseEntity.ok(service.historico(moradiaId));
    }

    @PostMapping("/{id}/encerrar")
    public ResponseEntity<ContratoPropriedadeResponse> encerrar(@PathVariable Long id) {
        return ResponseEntity.ok(service.encerrar(id));
    }

    @PostMapping("/{id}/rescindir")
    public ResponseEntity<ContratoPropriedadeResponse> rescindir(@PathVariable Long id) {
        return ResponseEntity.ok(service.rescindir(id));
    }
}
