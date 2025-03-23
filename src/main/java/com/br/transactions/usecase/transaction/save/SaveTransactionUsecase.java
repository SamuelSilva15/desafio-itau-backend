package com.br.transactions.usecase.transaction.save;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;

public interface SaveTransactionUsecase {
    void execute(SaveTransactionDTO saveTransactionDTO);
}