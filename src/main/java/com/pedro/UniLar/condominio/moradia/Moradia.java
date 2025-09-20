package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.bloco.Bloco;
import com.pedro.UniLar.condominio.moradia.enums.TipoMoradia;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "moradias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moradia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMoradia;

    private Integer numero;

    private Double area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMoradia tipo;

    // Tipologia textual, ex: T0, T1, T2... Opcional
    @Column(length = 20)
    private String tipologia;

    @Column(precision = 12, scale = 2)
    private BigDecimal quota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloco_id")
    private Bloco bloco;
}
