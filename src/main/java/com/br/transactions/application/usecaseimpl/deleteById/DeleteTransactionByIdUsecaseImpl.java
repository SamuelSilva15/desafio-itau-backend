package com.br.transactions.application.usecaseimpl.deleteById;

import com.br.transactions.application.gateway.transaction.TransactionGateway;
import com.br.transactions.usecase.transaction.deleteById.DeleteTransactionByIdUsecase;
import org.springframework.stereotype.Service;

@Service
public class DeleteTransactionByIdUsecaseImpl implements DeleteTransactionByIdUsecase {

    private final TransactionGateway transactionGateway;

    public DeleteTransactionByIdUsecaseImpl(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    @Override
    public void execute(Long transactionId) {
        transactionGateway.deleteById(transactionId);
    }
}
