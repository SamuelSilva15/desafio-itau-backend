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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class TransactionGatewayImpl implements TransactionGateway {

    private final TransactionRepository transactionRepository;
    private final Validator validator;
    private final Logger logger = LoggerFactory.getLogger(TransactionGatewayImpl.class);

    @Override
    @Transactional
    public void saveTransaction(SaveTransactionDTO saveTransactionDTO) throws BadRequestException {
        logger.info("Starting saving transaction");
        validateSaveTransactionDTO(saveTransactionDTO);

        try {
            Transaction transaction = new Transaction(saveTransactionDTO);
            transactionRepository.save(transaction);
            logger.info("Sucess to save transaction");
        } catch (Exception e) {
            logger.error("Error to save transaction: {}", e.getMessage());
            throw new BadRequestException();
        }

        logger.info("End of save transaction");
    }

    @Override
    public void deleteById(Long transactionId) {
        logger.info("Starting delete transaction");

        try {
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(EntityNotFoundException::new) ;
            transactionRepository.delete(transaction);
            logger.info("Sucess to delete transaction");
        } catch (Exception e) {
            logger.error("Error to delete transaction: {}", e.getMessage());
            throw new TransactionException();
        }

        logger.info("End of delete transaction");
    }

    @Override
    public GetStatisticLastMinuteDTO getStatisticLastMinuteDTO() {
        logger.info("Starting to get last minute statistics");

        try {
            return transactionRepository.findStatisticDataHoraBetween(OffsetDateTime.now().minusMinutes(1), OffsetDateTime.now());
        } catch (Exception e) {
            logger.error("Error to get last minute statistics: {}", e.getMessage());
            throw new TransactionException();
        }
    }

    private void validateSaveTransactionDTO(SaveTransactionDTO saveTransactionDTO) {
        logger.info("Starting validations to SaveTransactionDTO");

        Set<ConstraintViolation<SaveTransactionDTO>> violations = validator.validate(saveTransactionDTO);
        if (!violations.isEmpty()) {
            logger.error("Errors to validate SaveTransactionDTO: {}", violations);
            throw new TransactionException();
        }

        logger.info("End of validations to SaveTransactionDTO");
    }
}