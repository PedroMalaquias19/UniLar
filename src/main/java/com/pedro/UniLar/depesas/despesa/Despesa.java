package com.pedro.UniLar.depesas.despesa;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.depesas.categoria.Categoria;
import com.pedro.UniLar.pagamento.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "despesas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despesa")
    private Long idDespesa;

    @Column(nullable = false, length = 300)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "condominio_id")
    private Condominio condominio;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal montante;

    private LocalDate dataPagamento;

    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusPagamento estado = StatusPagamento.PENDENTE;

    private String fornecedor;

    // Caminho do documento/fatura anexado
    private String factura;

    public void anexarDocumento(String filename) {
        this.factura = filename;
    }

    public void marcarComoAtrasada() {
        this.estado = StatusPagamento.VENCIDO;
    }
}
