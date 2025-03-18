package com.br.transactions.infra.controller;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.usecase.transaction.SaveTransactionUsecase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransacaoController {

    private final SaveTransactionUsecase saveTransactionUsecase;

    public TransacaoController(SaveTransactionUsecase saveTransactionUsecase) {
        this.saveTransactionUsecase = saveTransactionUsecase;
    }

    @PostMapping
    ResponseEntity<HttpStatus> salvaTransacao(@RequestBody @Valid SaveTransactionDTO saveTransactionDTO) {
        try {
            saveTransactionUsecase.execute(saveTransactionDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}