package com.pedro.UniLar.profile.user.entities;

import com.pedro.UniLar.profile.user.enums.TipoCondomino;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "condominos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Condomino extends User {

    @Enumerated(EnumType.STRING)
    private TipoCondomino tipo;
}

