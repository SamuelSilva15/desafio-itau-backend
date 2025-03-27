package com.br.transactions.infra.service;

import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;
import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.infra.entity.transaction.Transaction;
import com.br.transactions.infra.exception.transaction.TransactionException;
import com.br.transactions.infra.repository.transaction.TransactionRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
    void shouldSaveTransactionSucessfully() {
        Set<ConstraintViolation<SaveTransactionDTO>> violations = new HashSet<>();
        when(validator.validate(saveTransactionDTO)).thenReturn(violations);

        transactionGatewayImpl.saveTransaction(saveTransactionDTO);

        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionArgumentCaptor.capture());
    }

    @Test
    void shouldThrowBadRequestExceptionWhenTransactionFails() {
        doThrow(new RuntimeException()).when(transactionRepository).save(any(Transaction.class));

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.saveTransaction(saveTransactionDTO);
        });

        assertEquals(TransactionException.class, exception.getClass());
        assertEquals("Error to save transaction.", exception.getMessage());
    }

    @Test
    void shouldThrowTransactionExceptionWhenViolationsInSaveTransactionDTOIsNotEmpty() {

        Set<ConstraintViolation<SaveTransactionDTO>> violations = new HashSet<>();
        violations.add(mock(ConstraintViolation.class));

        when(validator.validate(saveTransactionDTO)).thenReturn(violations);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.saveTransaction(saveTransactionDTO);
        });

        assertEquals(TransactionException.class, exception.getClass());
        assertEquals("Error to save transaction: " + exception.getCause(), exception.getMessage());
    }

    @Test
    void shouldDeleteTransactionSucessfully() {
        when(transactionRepository.findById(any())).thenReturn(Optional.of(new Transaction(saveTransactionDTO)));
        doNothing().when(transactionRepository).delete(any());

        transactionGatewayImpl.deleteById(1L);

        verify(transactionRepository).delete(any());
    }

    @Test
    void shouldThrowTransactionExceptionWhenTransactionIdIsNotFound() {
        when(transactionRepository.findById(any())).thenReturn(Optional.of(new Transaction(saveTransactionDTO)));
        doThrow(new TransactionException("Error to delete transaction: transaction not found")).when(transactionRepository).delete(any());

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.deleteById(1L);
        });

        assertEquals(TransactionException.class, exception.getClass());
        assertEquals("Error to delete transaction: transaction not found.", exception.getMessage());
    }

    @Test
    void shouldGetTransactionsLastMinuteSucessfully() {
        GetStatisticLastMinuteDTO getStatisticLastMinuteDTO = getStatisticLastMinuteDTO();
        when(transactionRepository.findStatisticDataHoraBetween(any(), any())).thenReturn(getStatisticLastMinuteDTO);

        GetStatisticLastMinuteDTO statisticLastMinuteDTO = transactionGatewayImpl.getStatisticLastMinuteDTO(60L);

        verify(transactionRepository).findStatisticDataHoraBetween(any(), any());
        assertNotNull(statisticLastMinuteDTO);
        assertEquals(getStatisticLastMinuteDTO.count(), statisticLastMinuteDTO.count());
        assertEquals(getStatisticLastMinuteDTO.sum(), statisticLastMinuteDTO.sum());
        assertEquals(getStatisticLastMinuteDTO.avg(), statisticLastMinuteDTO.avg());
        assertEquals(getStatisticLastMinuteDTO.min(), statisticLastMinuteDTO.min());
        assertEquals(getStatisticLastMinuteDTO.max(), statisticLastMinuteDTO.max());
    }

    @Test
    void shouldThrowTransactionExceptionWhenQueryReturnsError() {
        doThrow(new RuntimeException()).when(transactionRepository).findStatisticDataHoraBetween(any(), any());

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.getStatisticLastMinuteDTO(60L);
        });

        assertEquals(TransactionException.class, exception.getClass());
        assertEquals("Error to get last minute statistics.", exception.getMessage());
    }


    private SaveTransactionDTO createSaveTransactionDTO() {
        return new SaveTransactionDTO(1F, OffsetDateTime.now());
    }

    private GetStatisticLastMinuteDTO getStatisticLastMinuteDTO() {
        return new GetStatisticLastMinuteDTO(2L, new BigDecimal("44.44"), new BigDecimal("11.60"), new BigDecimal("11.60"), new BigDecimal("15.60"));
    }
}