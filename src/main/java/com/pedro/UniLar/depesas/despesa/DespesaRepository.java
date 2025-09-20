package com.pedro.UniLar.depesas.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query("select d from Despesa d where d.condominio.idCondominio = :condominioId")
    List<Despesa> findByCondominio(@Param("condominioId") Long condominioId);

    List<Despesa> findByCategoria_IdCategoria(Long categoriaId);
}
