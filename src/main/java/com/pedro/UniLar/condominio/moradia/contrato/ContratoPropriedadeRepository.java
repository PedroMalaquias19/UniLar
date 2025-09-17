package com.pedro.UniLar.condominio.moradia.contrato;

import com.pedro.UniLar.condominio.moradia.contrato.enums.StatusContratoPropriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContratoPropriedadeRepository extends JpaRepository<ContratoPropriedade, Long> {

    @Query("select c from ContratoPropriedade c where c.moradia.idMoradia = :moradiaId and c.status = 'ATIVO'")
    Optional<ContratoPropriedade> findAtivoByMoradia(Long moradiaId);

    boolean existsByMoradia_IdMoradiaAndStatus(Long moradiaId, StatusContratoPropriedade status);

    List<ContratoPropriedade> findByMoradia_IdMoradiaOrderByInicioDesc(Long moradiaId);
}
