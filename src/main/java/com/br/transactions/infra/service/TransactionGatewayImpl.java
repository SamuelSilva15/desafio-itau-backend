package com.br.transactions.infra.service;

import com.br.transactions.application.gateway.transaction.TransactionGateway;
import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;
import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.infra.entity.transaction.Transaction;
import com.br.transactions.infra.exception.transaction.TransactionException;
import com.br.transactions.infra.repository.transaction.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class TransactionGatewayImpl implements TransactionGateway {

    private final TransactionRepository transactionRepository;
    private final Validator validator;

    @Override
    @Transactional
    public void saveTransaction(SaveTransactionDTO saveTransactionDTO) throws BadRequestException {
        validateSaveTransactionDTO(saveTransactionDTO);

        try {
            Transaction transaction = new Transaction(saveTransactionDTO);
            transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new BadRequestException();
        }

    }

    @Override
    public void deleteById(Long transactionId) {
        try {
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(EntityNotFoundException::new) ;
            transactionRepository.delete(transaction);
        } catch (Exception e) {
            throw new TransactionException();
        }
    }

    @Override
    public GetStatisticLastMinuteDTO getStatisticLastMinuteDTO() {
        try {
            return transactionRepository.findStatisticDataHoraBetween(OffsetDateTime.now().minusMinutes(1), OffsetDateTime.now());
        } catch (Exception e) {
            throw new TransactionException();
        }
    }

    private void validateSaveTransactionDTO(SaveTransactionDTO saveTransactionDTO) {
        Set<ConstraintViolation<SaveTransactionDTO>> violations = validator.validate(saveTransactionDTO);
        if (!violations.isEmpty()) {
            throw new TransactionException();
        }
    }
}