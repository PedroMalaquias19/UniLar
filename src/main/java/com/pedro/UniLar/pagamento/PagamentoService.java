package com.pedro.UniLar.pagamento;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.condominio.condominio.CondominioRepository;
import com.pedro.UniLar.condominio.moradia.Moradia;
import com.pedro.UniLar.condominio.moradia.MoradiaRepository;
import com.pedro.UniLar.exception.NotFoundException;
import com.pedro.UniLar.file.FileService;
import com.pedro.UniLar.pagamento.dto.PagamentoRequest;
import com.pedro.UniLar.pagamento.enums.StatusPagamento;
import com.pedro.UniLar.pagamento.enums.TipoPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final MoradiaRepository moradiaRepository;
    private final CondominioRepository condominioRepository;
    private final FileService fileService;

    public Pagamento createManual(PagamentoRequest request) {
        Moradia m = moradiaRepository.findById(request.moradiaId())
                .orElseThrow(() -> new NotFoundException("Moradia não encontrada"));
        Pagamento pagamento = PagamentoMapper.toEntity(request);
        pagamento.setMoradia(m);
        if (pagamento.getMontante() == null) {
            pagamento.setMontante(m.getQuota() != null ? m.getQuota() : BigDecimal.ZERO);
        }
        if (pagamento.getTipo() == null)
            pagamento.setTipo(TipoPagamento.QUOTA);
        if (pagamento.getStatusPagamento() == null)
            pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        if (pagamento.getDataCobranca() == null)
            pagamento.setDataCobranca(LocalDate.now());
        if (pagamento.getVencimento() == null)
            pagamento.setVencimento(pagamento.getDataCobranca());
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listByMoradia(Long moradiaId) {
        return pagamentoRepository.findByMoradia_IdMoradia(moradiaId);
    }

    public List<Pagamento> listByCondominio(Long condominioId) {
        return pagamentoRepository.findByCondominio(condominioId);
    }

    public Pagamento getById(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));
    }

    @Transactional
    public Pagamento confirmarPagamento(Long pagamentoId, MultipartFile comprovativoFile) {
        Pagamento p = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        String filename = fileService.uploadDocument("pagamentos", pagamentoId, comprovativoFile);
        p.setComprovativo(filename);
        p.setDataPagamento(LocalDate.now());
        p.setStatusPagamento(StatusPagamento.PAGO);
        return pagamentoRepository.save(p);
    }

    // Executa diariamente às 01:15 para gerar pagamentos do dia
    @Scheduled(cron = "0 15 1 * * *")
    @Transactional
    public void gerarPagamentosDoDia() {
        LocalDate hoje = LocalDate.now();

        // Descobrir os condomínios cujo dia de cobrança é hoje
        List<Condominio> condominios = condominioRepository.findAll();
        for (Condominio c : condominios) {
            if (c.getDiaCobranca() == null)
                continue;
            YearMonth ym = YearMonth.from(hoje);
            int day = Math.min(c.getDiaCobranca(), ym.lengthOfMonth());
            if (hoje.getDayOfMonth() != day)
                continue;

            int tolerancia = c.getToleranciaDias() != null ? c.getToleranciaDias() : 0;
            LocalDate vencimento = hoje.plusDays(tolerancia);

            // Para cada moradia do condomínio, gerar um pagamento se ainda não existir para
            // este mês
            List<Moradia> moradias = moradiaRepository.findByBloco_Condominio_IdCondominio(c.getIdCondominio());
            for (Moradia m : moradias) {
                // Evitar duplicados: checar se já existe cobrança deste mês
                List<Pagamento> existentes = pagamentoRepository.findQuotaByMoradiaAndDataCobranca(m.getIdMoradia(),
                        hoje);
                if (!existentes.isEmpty())
                    continue;

                BigDecimal montante = m.getQuota() != null ? m.getQuota()
                        : (c.getQuota() != null ? c.getQuota() : BigDecimal.ZERO);
                Pagamento novo = Pagamento.builder()
                        .moradia(m)
                        .montante(montante)
                        .statusPagamento(StatusPagamento.PENDENTE)
                        .tipo(TipoPagamento.QUOTA)
                        .dataCobranca(hoje)
                        .vencimento(vencimento)
                        .build();
                pagamentoRepository.save(novo);
            }
        }
    }
}
