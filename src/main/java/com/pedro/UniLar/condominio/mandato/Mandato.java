package com.pedro.UniLar.condominio.mandato;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.profile.user.entities.Sindico;
import com.pedro.UniLar.condominio.mandato.enums.StatusContrato;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "mandatos", uniqueConstraints = {
        @UniqueConstraint(name = "uk_mandato_condominio_ativo", columnNames = { "condominio_id", "status" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mandato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMandato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sindico_id")
    private Sindico sindico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "condominio_id")
    private Condominio condominio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusContrato statusContrato;

    @Column(nullable = false)
    private LocalDate inicioMandato;

    private LocalDate fimMandato;

    @Column(precision = 12, scale = 2)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusContrato status;

    private String contratoUrl;
}
