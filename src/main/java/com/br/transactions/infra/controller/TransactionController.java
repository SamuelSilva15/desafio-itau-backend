package com.br.transactions.infra.controller;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.usecase.transaction.SaveTransactionUsecase;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final SaveTransactionUsecase saveTransactionUsecase;

    @PostMapping
    ResponseEntity<HttpStatus> salvaTransacao(@RequestBody SaveTransactionDTO saveTransactionDTO) throws BadRequestException {
        saveTransactionUsecase.execute(saveTransactionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public TransactionController(SaveTransactionUsecase saveTransactionUsecase) {
        this.saveTransactionUsecase = saveTransactionUsecase;
    }
}