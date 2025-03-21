package com.br.transactions.infra.repository.transaction;

import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;
import com.br.transactions.infra.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO(COUNT(t.transactionId), COALESCE(SUM(t.valor), 0), COALESCE(AVG(t.valor), 0), " +
            "COALESCE(MAX(t.valor), 0), COALESCE(MIN(t.valor), 0)) as max FROM Transaction t WHERE t.dataHora >= ?1 AND t.dataHora <= ?2")
    GetStatisticLastMinuteDTO findStatisticyDataHoraBetween(OffsetDateTime dataHora, OffsetDateTime dataHora2);

    List<Transaction> findAllByDataHoraBetween(OffsetDateTime dataHora, OffsetDateTime dataHora2);
}