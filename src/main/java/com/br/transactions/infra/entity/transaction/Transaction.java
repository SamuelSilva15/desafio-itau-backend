package com.br.transactions.infra.entity.transaction;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    private float valor;

    private OffsetDateTime dataHora;

    public Transaction(SaveTransactionDTO saveTransactionDTO) {
        this.valor = saveTransactionDTO.valor();
        this.dataHora = saveTransactionDTO.dataHora();
    }
}