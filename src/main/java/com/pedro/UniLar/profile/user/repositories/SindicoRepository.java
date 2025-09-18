package com.pedro.UniLar.profile.user.repositories;

import com.pedro.UniLar.profile.user.entities.Sindico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SindicoRepository extends JpaRepository<Sindico, Long> {
    @Query("select s from Sindico s left join com.pedro.UniLar.condominio.mandato.Mandato m on m.sindico = s and m.statusContrato = 'ATIVO' where m.idMandato is null")
    List<Sindico> findAllWithoutActiveMandate();
}
