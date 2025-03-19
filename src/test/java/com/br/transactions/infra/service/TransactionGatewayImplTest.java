package com.br.transactions.infra.service;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.infra.entity.transaction.Transaction;
import com.br.transactions.infra.exception.transaction.TransactionException;
import com.br.transactions.infra.repository.transaction.TransactionRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionGatewayImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private TransactionGatewayImpl transactionGatewayImpl;

    private SaveTransactionDTO saveTransactionDTO;

    @BeforeEach
    void setup() {
        saveTransactionDTO = createSaveTransactionDTO();
    }

    @Test
    void saveTransaction() throws BadRequestException {
        Set<ConstraintViolation<SaveTransactionDTO>> violations = new HashSet<>();
        when(validator.validate(saveTransactionDTO)).thenReturn(violations);

        transactionGatewayImpl.saveTransaction(saveTransactionDTO);
    }

    @Test
    void shouldThrowBadRequestExceptionWhenTransactionFails() {
        doThrow(new RuntimeException()).when(transactionRepository).save(any(Transaction.class));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            transactionGatewayImpl.saveTransaction(saveTransactionDTO);
        });

        assertTrue(BadRequestException.class.equals(exception.getClass()));}

    @Test
    void throwTransactionExceptionWhenViolationsInSaveTransactionDTOIsNotEmpty() {
        Set<ConstraintViolation<SaveTransactionDTO>> violations = mock(Set.class);
        when(violations.isEmpty()).thenReturn(false);

        when(validator.validate(saveTransactionDTO)).thenReturn(violations);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.saveTransaction(saveTransactionDTO);
        });

        assertTrue(TransactionException.class.equals(exception.getClass()));
    }

    @Test
    void throwTransactionExceptionWhen() {
        Set<ConstraintViolation<SaveTransactionDTO>> violations = mock(Set.class);
        when(violations.isEmpty()).thenReturn(false);

        when(validator.validate(saveTransactionDTO)).thenReturn(violations);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.saveTransaction(saveTransactionDTO);
        });

        assertTrue(TransactionException.class.equals(exception.getClass()));
    }

    private SaveTransactionDTO createSaveTransactionDTO() {
        return new SaveTransactionDTO(1F, OffsetDateTime.now());
    }
}