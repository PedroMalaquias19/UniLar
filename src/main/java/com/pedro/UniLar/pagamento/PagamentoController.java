package com.pedro.UniLar.pagamento;

import com.pedro.UniLar.pagamento.dto.PagamentoRequest;
import com.pedro.UniLar.pagamento.dto.PagamentoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final com.pedro.UniLar.file.FileService fileService;

    @GetMapping("/moradia/{moradiaId}")
    public List<PagamentoResponse> listByMoradia(@PathVariable Long moradiaId) {
        return pagamentoService.listByMoradia(moradiaId).stream().map(PagamentoMapper::toResponse).toList();
    }

    @GetMapping("/condominio/{condominioId}")
    public List<PagamentoResponse> listByCondominio(@PathVariable Long condominioId) {
        return pagamentoService.listByCondominio(condominioId).stream().map(PagamentoMapper::toResponse).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PagamentoResponse createManual(@RequestBody PagamentoRequest req) {
        return PagamentoMapper.toResponse(pagamentoService.createManual(req));
    }

    @PostMapping(value = "/{pagamentoId}/confirmar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PagamentoResponse confirmarPagamento(@PathVariable Long pagamentoId,
            @RequestPart("file") MultipartFile file) {
        return PagamentoMapper.toResponse(pagamentoService.confirmarPagamento(pagamentoId, file));
    }

    @GetMapping(value = "/{pagamentoId}/comprovativo")
    public ResponseEntity<byte[]> downloadComprovativo(@PathVariable Long pagamentoId) {
        Pagamento target = pagamentoService.getById(pagamentoId);
        byte[] bytes = fileService.download(target.getComprovativo());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"comprovativo-" + pagamentoId + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }
}
