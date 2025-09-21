package com.pedro.UniLar.condominio.mandato;

import com.pedro.UniLar.condominio.mandato.enums.StatusContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface MandatoRepository extends JpaRepository<Mandato, Long> {

        @Query("select m from Mandato m where m.condominio.idCondominio = :condominioId and m.statusContrato = 'ATIVO'")
        Optional<Mandato> findMandatoAtivo(Long condominioId);

        boolean existsByCondominio_IdCondominioAndStatusContrato(Long condominioIdCondominio,
                        StatusContrato statusContrato);

        boolean existsByCondominio_IdCondominioAndSindico_IdUsuarioAndStatusContrato(Long condominioIdCondominio,
                        Long sindicoIdUsuario, StatusContrato statusContrato);

        @Query("select m from Mandato m join fetch m.condominio where m.sindico.idUsuario = :sindicoId")
        List<Mandato> findAllBySindicoIdWithCondominio(Long sindicoId);
}