package com.br.transactions.infra.repository.transaction;

import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;
import com.br.transactions.infra.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO( " +
            "COUNT(t.transactionId), " +
            "CAST(COALESCE(SUM(t.valor), 0) AS BigDecimal), " +
            "CAST(COALESCE(AVG(t.valor), 0) AS BigDecimal), " +
            "CAST(COALESCE(MIN(t.valor), 0) AS BigDecimal), " +
            "CAST(COALESCE(MAX(t.valor), 0) AS BigDecimal)) " +
            "FROM Transaction t WHERE t.dataHora BETWEEN ?1 AND ?2")
    GetStatisticLastMinuteDTO findStatisticDataHoraBetween(OffsetDateTime inicio, OffsetDateTime fim);

}