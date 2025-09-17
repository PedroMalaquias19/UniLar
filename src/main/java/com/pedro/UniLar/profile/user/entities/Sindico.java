package com.pedro.UniLar.profile.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedro.UniLar.condominio.mandato.Mandato;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sindicos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sindico extends User {

    @OneToMany(mappedBy = "sindico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Mandato> mandatos = new ArrayList<>();

    public void adicionarMandato(Mandato mandato) {
        mandato.setSindico(this);
        this.mandatos.add(mandato);
    }

    public void removerMandato(Mandato mandato) {
        this.mandatos.remove(mandato);
        mandato.setSindico(null);
    }
}
