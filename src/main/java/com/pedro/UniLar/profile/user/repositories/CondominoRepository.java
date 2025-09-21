package com.pedro.UniLar.profile.user.repositories;

import com.pedro.UniLar.profile.user.entities.Condomino;
import com.pedro.UniLar.profile.user.enums.TipoCondomino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.repository.query.Param;

@Repository
public interface CondominoRepository extends JpaRepository<Condomino, Long> {

        @Query("select distinct c from com.pedro.UniLar.condominio.moradia.contrato.ContratoPropriedade cp " +
                        " join cp.moradia m " +
                        " join m.bloco b " +
                        " join b.condominio cond " +
                        " join cp.proprietario c " +
                        " where cond.idCondominio = :condominioId")
        List<Condomino> findAllByCondominio(Long condominioId);

        @Query("select distinct c from com.pedro.UniLar.condominio.moradia.contrato.ContratoPropriedade cp " +
                        " join cp.moradia m " +
                        " join m.bloco b " +
                        " join b.condominio cond " +
                        " join cp.proprietario c " +
                        " where cond.idCondominio = :condominioId and c.tipo = :tipo")
        List<Condomino> findAllByCondominioAndTipo(Long condominioId, TipoCondomino tipo);

        @Query("select c from Condomino c where c.moradia.idMoradia = :moradiaId and c.tipo in :tipos")
        List<Condomino> findByMoradiaAndTipos(@Param("moradiaId") Long moradiaId,
                        @Param("tipos") List<TipoCondomino> tipos);
}
