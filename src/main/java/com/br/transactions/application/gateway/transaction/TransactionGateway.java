package com.br.transactions.application.gateway.transaction;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import org.apache.coyote.BadRequestException;

public interface TransactionGateway {
    void saveTransaction(SaveTransactionDTO saveTransactionDTO) throws BadRequestException;
    void deleteById(Long transactionId);
}