package com.br.transactions.infra.controller;

import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;
import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.usecase.statistic.GetStatisticUsecase;
import com.br.transactions.usecase.transaction.deleteById.DeleteTransactionByIdUsecase;
import com.br.transactions.usecase.transaction.save.SaveTransactionUsecase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("transacao")
public class TransactionController {

    private final SaveTransactionUsecase saveTransactionUsecase;
    private final DeleteTransactionByIdUsecase deleteTransactionByIdUsecase;
    private final GetStatisticUsecase getStatisticUsecase;

    @Operation(summary = "Endpoint to save transaction")
    @PostMapping
    ResponseEntity<HttpStatus> saveTransaction(@RequestBody SaveTransactionDTO saveTransactionDTO) {
        this.saveTransactionUsecase.execute(saveTransactionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Endpoint to delete transaction by id")
    @DeleteMapping("/{transactionId}")
    ResponseEntity<HttpStatus> deleteTransaction(@PathVariable("transactionId") Long transactionId)  {
        this.deleteTransactionByIdUsecase.execute(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Endpoint to get the transaction statistics of the last minute")
    @GetMapping("/estatistica")
    ResponseEntity<GetStatisticLastMinuteDTO> getEstatistica(@RequestParam(value = "time", defaultValue = "60") Long time) {
        return ResponseEntity.ok(this.getStatisticUsecase.execute(time));
    }
}