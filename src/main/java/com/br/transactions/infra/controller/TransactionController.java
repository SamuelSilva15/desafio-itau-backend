package com.br.transactions.infra.controller;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.usecase.transaction.save.SaveTransactionUsecase;
import com.br.transactions.usecase.transaction.deleteById.DeleteTransactionByIdUsecase;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final SaveTransactionUsecase saveTransactionUsecase;
    private final DeleteTransactionByIdUsecase deleteTransactionByIdUsecase;

    public TransactionController(SaveTransactionUsecase saveTransactionUsecase, DeleteTransactionByIdUsecase deleteTransactionByIdUsecase) {
        this.saveTransactionUsecase = saveTransactionUsecase;
        this.deleteTransactionByIdUsecase = deleteTransactionByIdUsecase;
    }

    @PostMapping
    ResponseEntity<HttpStatus> salvaTransacao(@RequestBody SaveTransactionDTO saveTransactionDTO) throws BadRequestException {
        saveTransactionUsecase.execute(saveTransactionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{transactionId}")
    ResponseEntity<HttpStatus> deletaTransacao(@PathVariable("transactionId") Long transactionId)  {
        deleteTransactionByIdUsecase.execute(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}