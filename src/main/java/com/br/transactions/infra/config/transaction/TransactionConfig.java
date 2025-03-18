package com.br.transactions.infra.config.transaction;

import com.br.transactions.application.gateway.transaction.TransactionGateway;
import com.br.transactions.application.usecaseimpl.SaveTransactionUsecaseImpl;
import com.br.transactions.usecase.transaction.SaveTransactionUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {

    @Bean
    public SaveTransactionUsecase saveTransactionUsecase(TransactionGateway transactionGateway) {
        return new SaveTransactionUsecaseImpl(transactionGateway);
    }
}