package com.pedro.UniLar.depesas.categoria;

import com.pedro.UniLar.condominio.condominio.Condominio;
import com.pedro.UniLar.depesas.despesa.Despesa;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(nullable = false, length = 150)
    private String nome;

    // Se null => categoria global (livre). Se não-null => categoria específica de
    // um condomínio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominio_id")
    private Condominio condominio;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Despesa> despesas = new ArrayList<>();

    public void adicionarDespesa(Despesa despesa) {
        despesa.setCategoria(this);
        this.despesas.add(despesa);
    }

    public void removerDespesa(Despesa despesa) {
        this.despesas.remove(despesa);
        despesa.setCategoria(null);
    }
}
