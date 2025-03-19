package com.br.transactions.usecase.transaction;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import org.apache.coyote.BadRequestException;

public interface SaveTransactionUsecase {
    void execute(SaveTransactionDTO saveTransactionDTO) throws BadRequestException;
}