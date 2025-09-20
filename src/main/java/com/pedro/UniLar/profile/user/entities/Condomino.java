package com.pedro.UniLar.profile.user.entities;

import com.pedro.UniLar.condominio.moradia.Moradia;
import com.pedro.UniLar.profile.user.enums.TipoCondomino;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "condominos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Condomino extends User {

    @Enumerated(EnumType.STRING)
    private TipoCondomino tipo;

    // Para DEPENDENTE, FUNCIONARIO, OUTRO: vínculo direto com uma moradia
    // Proprietário/Inquilino não devem usar este campo (são associados via
    // contrato)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moradia_id")
    private Moradia moradia;
}
