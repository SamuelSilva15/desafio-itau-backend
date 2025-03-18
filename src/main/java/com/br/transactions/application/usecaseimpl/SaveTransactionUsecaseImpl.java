package com.br.transactions.application.usecaseimpl;

import com.br.transactions.application.gateway.transaction.TransactionGateway;
import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.usecase.transaction.SaveTransactionUsecase;

public class SaveTransactionUsecaseImpl implements SaveTransactionUsecase {

    private final TransactionGateway transactionGateway;

    public SaveTransactionUsecaseImpl(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    @Override
    public void execute(SaveTransactionDTO saveTransactionDTO) {
        transactionGateway.saveTransaction(saveTransactionDTO);
    }
}