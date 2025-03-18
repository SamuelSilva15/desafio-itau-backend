package com.br.transactions.application.gateway.transaction;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;

public interface TransactionGateway {
    void saveTransaction(SaveTransactionDTO saveTransactionDTO);
}