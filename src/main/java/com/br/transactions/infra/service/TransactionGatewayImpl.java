package com.br.transactions.infra.service;

import com.br.transactions.application.gateway.transaction.TransactionGateway;
import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.infra.entity.transaction.Transaction;
import com.br.transactions.infra.repository.transaction.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionGatewayImpl implements TransactionGateway {

    private final TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(SaveTransactionDTO saveTransactionDTO) {
        Transaction transaction = new Transaction(saveTransactionDTO);
        transactionRepository.save(transaction);
    }
}