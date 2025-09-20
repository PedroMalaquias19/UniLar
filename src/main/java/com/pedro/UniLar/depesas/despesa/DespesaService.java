package com.pedro.UniLar.depesas.despesa;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.condominio.condominio.CondominioService;
import com.pedro.UniLar.depesas.categoria.Categoria;
import com.pedro.UniLar.depesas.categoria.CategoriaService;
import com.pedro.UniLar.depesas.despesa.dto.DespesaRequest;
import com.pedro.UniLar.depesas.despesa.dto.DespesaResponse;
import com.pedro.UniLar.exception.NotFoundException;
import com.pedro.UniLar.file.FileService;
import com.pedro.UniLar.pagamento.enums.StatusPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DespesaService {

    private final DespesaRepository repository;
    private final DespesaMapper mapper;
    private final CategoriaService categoriaService;
    private final CondominioService condominioService;
    private final FileService fileService;

    @Transactional
    public DespesaResponse create(DespesaRequest req) {
        Categoria categoria = req.categoriaId() != null ? categoriaService.getEntity(req.categoriaId()) : null;
        Condominio condominio = condominioService.getEntity(req.condominioId());
        // Validação: se categoria estiver ligada a condomínio, deve ser o mesmo;
        // categorias globais são permitidas em qualquer condomínio
        if (categoria != null && categoria.getCondominio() != null
                && !categoria.getCondominio().getIdCondominio().equals(condominio.getIdCondominio())) {
            throw new com.pedro.UniLar.exception.BadRequestException(
                    "Categoria não pertence ao condomínio informado");
        }
        Despesa entity = mapper.toEntity(req, categoria, condominio);
        if (entity.getDataVencimento() == null && entity.getDataPagamento() == null) {
            entity.setDataVencimento(LocalDate.now());
        }
        entity = repository.save(entity);
        return mapper.toResponse(entity);
    }

    public List<DespesaResponse> listByCondominio(Long condominioId) {
        return repository.findByCondominio(condominioId).stream().map(mapper::toResponse).toList();
    }

    public Despesa getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Despesa não encontrada: " + id));
    }

    @Transactional
    public DespesaResponse update(Long id, DespesaRequest req) {
        Despesa entity = getEntity(id);
        Categoria categoria = req.categoriaId() != null ? categoriaService.getEntity(req.categoriaId()) : null;
        Condominio condominio = condominioService.getEntity(req.condominioId());
        if (categoria != null && categoria.getCondominio() != null
                && !categoria.getCondominio().getIdCondominio().equals(condominio.getIdCondominio())) {
            throw new com.pedro.UniLar.exception.BadRequestException(
                    "Categoria não pertence ao condomínio informado");
        }
        mapper.update(entity, req, categoria, condominio);
        return mapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    @Transactional
    public DespesaResponse anexarFatura(Long idDespesa, MultipartFile file) {
        Despesa entity = getEntity(idDespesa);
        String filename = fileService.uploadDocument("despesas", idDespesa, file);
        entity.setFactura(filename);
        return mapper.toResponse(entity);
    }

    @Transactional
    public DespesaResponse marcarComoAtrasada(Long idDespesa) {
        Despesa entity = getEntity(idDespesa);
        entity.setEstado(StatusPagamento.VENCIDO);
        return mapper.toResponse(entity);
    }
}
