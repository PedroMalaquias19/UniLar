package com.pedro.UniLar.condominio.moradia.morador;

import com.pedro.UniLar.condominio.moradia.contrato.ContratoPropriedade;
import com.pedro.UniLar.profile.user.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "moradores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Morador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMorador;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id")
    private ContratoPropriedade contrato;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User usuario; // pode ser o proprietário (duplicidade evitada via regra) ou dependente / inquilino

    @Column(nullable = false)
    private boolean proprietario; // true se este morador é o proprietário do contrato vigente
}
