package com.br.transactions.usecase.transaction.save;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import org.apache.coyote.BadRequestException;

public interface SaveTransactionUsecase {
    void execute(SaveTransactionDTO saveTransactionDTO) throws BadRequestException;
}