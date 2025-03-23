package com.br.transactions.infra.exception.transaction;

public class TransactionException extends IllegalArgumentException{

    public TransactionException(String message) {
        super(message);
    }
}
