package com.pedro.UniLar.profile.user.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sindicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sindico extends User {

    @Column(nullable = false)
    private LocalDate inicioMandato;

    private LocalDate fimMandato;

    private String contrato;
}
