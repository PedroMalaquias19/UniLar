package com.pedro.UniLar.condominio.bloco;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.condominio.moradia.Moradia;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blocos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bloco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloco;

    @Column(nullable = false, length = 100)
    private String nomeBloco;

    @Column(nullable = false)
    @Builder.Default
    private Integer numMoradias = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominio_id")
    private Condominio condominio;

    @OneToMany(mappedBy = "bloco", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Moradia> moradias = new ArrayList<>();

    public void adicionarMoradia(Moradia moradia) {
        moradia.setBloco(this);
        this.moradias.add(moradia);
        this.numMoradias = moradias.size();
    }

    public void removerMoradia(Moradia moradia) {
        this.moradias.remove(moradia);
        moradia.setBloco(null);
        this.numMoradias = moradias.size();
    }
}
