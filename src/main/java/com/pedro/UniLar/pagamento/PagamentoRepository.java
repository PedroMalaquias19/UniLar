package com.pedro.UniLar.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByMoradia_IdMoradia(Long moradiaId);

    @Query("select p from Pagamento p join p.moradia m join m.bloco b join b.condominio c where c.idCondominio = :condominioId")
    List<Pagamento> findByCondominio(@Param("condominioId") Long condominioId);

    @Query("select p from Pagamento p where p.moradia.idMoradia = :moradiaId and p.dataCobranca = :dataCobranca and p.tipo = com.pedro.UniLar.pagamento.enums.TipoPagamento.QUOTA")
    List<Pagamento> findQuotaByMoradiaAndDataCobranca(@Param("moradiaId") Long moradiaId,
            @Param("dataCobranca") LocalDate dataCobranca);
}
