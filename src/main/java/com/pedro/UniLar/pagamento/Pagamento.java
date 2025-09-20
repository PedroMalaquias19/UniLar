package com.pedro.UniLar.pagamento;

import com.pedro.UniLar.condominio.moradia.Moradia;
import com.pedro.UniLar.pagamento.enums.StatusPagamento;
import com.pedro.UniLar.pagamento.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal montante;

    // Caminho/filename do comprovativo armazenado localmente
    private String comprovativo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    // Data limite de pagamento (dia de cobrança + tolerância)
    private LocalDate vencimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TipoPagamento tipo = TipoPagamento.QUOTA;

    // Quando o morador efetuou o pagamento (ao enviar comprovativo)
    private LocalDate dataPagamento;

    // Quando o sistema gerou a cobrança
    @Column(nullable = false)
    private LocalDate dataCobranca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moradia_id")
    private Moradia moradia;
}
