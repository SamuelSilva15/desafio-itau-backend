package com.br.transactions.usecase.transaction;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;

public interface SaveTransactionUsecase {
    void execute(SaveTransactionDTO saveTransactionDTO);
}