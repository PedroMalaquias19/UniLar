package com.pedro.UniLar.pagamento;

import com.pedro.UniLar.pagamento.enums.StatusPagamento;
import com.pedro.UniLar.pagamento.enums.TipoPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "blocos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    private Long id_pagamento;
    private BigDecimal montante;
    private String comprovativo;
    private StatusPagamento statusPagamento;
    private Integer vencimento;
    private TipoPagamento tipo;
    private LocalDate dataPagamento;
    private LocalDate dataCobranca;

}
