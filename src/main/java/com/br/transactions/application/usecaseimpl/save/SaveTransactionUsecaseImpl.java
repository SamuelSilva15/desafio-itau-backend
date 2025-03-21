package com.br.transactions.application.usecaseimpl.save;

import com.br.transactions.application.gateway.transaction.TransactionGateway;
import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.usecase.transaction.save.SaveTransactionUsecase;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SaveTransactionUsecaseImpl implements SaveTransactionUsecase {

    private final TransactionGateway transactionGateway;

    @Override
    public void execute(SaveTransactionDTO saveTransactionDTO) throws BadRequestException {
        transactionGateway.saveTransaction(saveTransactionDTO);
    }
}