package com.pedro.UniLar.condominio.moradia;

import com.pedro.UniLar.condominio.bloco.Bloco;
import com.pedro.UniLar.condominio.moradia.enums.TipoMoradia;
import jakarta.persistence.*;
import com.pedro.UniLar.pagamento.Pagamento;
import com.pedro.UniLar.profile.user.entities.Condomino;
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

    @OneToMany(mappedBy = "moradia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private java.util.List<Pagamento> pagamentos = new java.util.ArrayList<>();

    // Moradores associados diretamente à moradia (DEPENDENTE, FUNCIONARIO, OUTRO)
    // Proprietário/Inquilino são geridos pela entidade ContratoPropriedade
    @OneToMany(mappedBy = "moradia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private java.util.List<Condomino> moradores = new java.util.ArrayList<>();

    public void adicionarMorador(Condomino condomino) {
        if (condomino == null)
            return;
        condomino.setMoradia(this);
        this.moradores.add(condomino);
    }

    public void removerMorador(Condomino condomino) {
        if (condomino == null)
            return;
        this.moradores.remove(condomino);
        condomino.setMoradia(null);
    }
}
