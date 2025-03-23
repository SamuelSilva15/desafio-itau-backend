package com.br.transactions.infra.handler.transaction;

import com.br.transactions.core.domain.response.ErrorResponseDTO;
import com.br.transactions.infra.exception.transaction.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class TransactionExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionException.class)
    private ResponseEntity<ErrorResponseDTO> handleTransactionException(TransactionException transactionException) {
        return new ResponseEntity<>(new ErrorResponseDTO(transactionException.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}