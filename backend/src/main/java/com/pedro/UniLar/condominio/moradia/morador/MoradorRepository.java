package com.pedro.UniLar.condominio.moradia.morador;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoradorRepository extends JpaRepository<Morador, Long> {
    List<Morador> findByContrato_IdContrato(Long contratoId);
    boolean existsByContrato_IdContratoAndProprietarioTrue(Long contratoId);
}
