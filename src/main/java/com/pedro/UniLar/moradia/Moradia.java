package com.pedro.UniLar.moradia;

import jakarta.persistence.*;

@Entity
public class Moradia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Nenhum outro campo além do diagrama
}
