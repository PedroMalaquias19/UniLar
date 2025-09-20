package com.pedro.UniLar.condominio.moradia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradiaRepository extends JpaRepository<Moradia, Long> {
    java.util.List<Moradia> findByBloco_Condominio_IdCondominio(Long condominioId);
}
