package com.pedro.UniLar.condominio.moradia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradiaRepository extends JpaRepository<Moradia, Long> {
    java.util.List<Moradia> findByBloco_Condominio_IdCondominio(Long condominioId);

    // Moradias onde o condómino é PROPRIETÁRIO (contrato ativo)
    @Query("select distinct cp.moradia from com.pedro.UniLar.condominio.moradia.contrato.ContratoPropriedade cp " +
            " where cp.proprietario.idUsuario = :condominoId and cp.status = com.pedro.UniLar.condominio.moradia.contrato.enums.StatusContratoPropriedade.ATIVO")
    java.util.List<Moradia> findByProprietario(@Param("condominoId") Long condominoId);

    // Moradias onde o condómino está diretamente vinculado como morador
    // (DEPENDENTE, FUNCIONARIO, OUTRO)
    @Query("select distinct m from Moradia m join m.moradores c where c.idUsuario = :condominoId")
    java.util.List<Moradia> findByMorador(@Param("condominoId") Long condominoId);
}
