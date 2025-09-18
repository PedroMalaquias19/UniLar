package com.pedro.UniLar.condominio.moradia.contrato;

import com.pedro.UniLar.condominio.moradia.Moradia;
import com.pedro.UniLar.condominio.moradia.contrato.enums.StatusContratoPropriedade;
import com.pedro.UniLar.profile.user.entities.Condomino;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "contratos_propriedade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoPropriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrato;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "moradia_id")
    private Moradia moradia;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id")
    private Condomino proprietario; // O proprietário vigente

    @Column(nullable = false)
    private LocalDate inicio;

    private LocalDate fim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusContratoPropriedade status;

    private String contratoUrl;

    public boolean ativo() {
        return status == StatusContratoPropriedade.ATIVO;
    }
}
