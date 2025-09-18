package com.pedro.UniLar.condominio.condominio;

import com.pedro.UniLar.condominio.mandato.Mandato;
import com.pedro.UniLar.condominio.bloco.Bloco;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "condominios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Condominio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCondominio;

    @Column(nullable = false, unique = true, length = 300)
    private String nome;

    @Column(length = 2000)
    private String descricao;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantBlocos = 0;

    @Column(precision = 12, scale = 2)
    private BigDecimal quota;

    @Column(precision = 12, scale = 2)
    private BigDecimal juros;

    @Column(precision = 12, scale = 2)
    private BigDecimal multaFixa;

    private Integer toleranciaDias;

    @Column(nullable = false)
    @Builder.Default
    private Integer diaCobranca = 1;

    @OneToMany(mappedBy = "condominio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Bloco> blocos = new ArrayList<>();

    @OneToMany(mappedBy = "condominio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Mandato> mandatos = new ArrayList<>();

    public void adicionarBloco(Bloco bloco) {
        bloco.setCondominio(this);
        this.blocos.add(bloco);
        this.quantBlocos = blocos.size();
    }

    public void removerBloco(Bloco bloco) {
        this.blocos.remove(bloco);
        bloco.setCondominio(null);
        this.quantBlocos = blocos.size();
    }

    public void adicionarMandato(Mandato mandato) {
        mandato.setCondominio(this);
        this.mandatos.add(mandato);
    }

    public void removerMandato(Mandato mandato) {
        this.mandatos.remove(mandato);
        mandato.setCondominio(null);
    }
}
