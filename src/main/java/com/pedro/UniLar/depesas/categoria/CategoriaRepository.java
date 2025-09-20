package com.pedro.UniLar.depesas.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Todas as categorias globais
    @Query("select c from Categoria c where c.condominio is null")
    List<Categoria> findGlobais();

    // Todas as categorias de um condomínio específico
    @Query("select c from Categoria c where c.condominio.idCondominio = :condominioId")
    List<Categoria> findByCondominio(@Param("condominioId") Long condominioId);

    // Categorias globais OU do condomínio especificado (left join fetch para trazer
    // o condominio quando existir)
    @Query("select distinct c from Categoria c left join fetch c.condominio cond where c.condominio is null or cond.idCondominio = :condominioId")
    List<Categoria> findGlobaisOuDoCondominio(@Param("condominioId") Long condominioId);
}
