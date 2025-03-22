package com.br.transactions.infra.service;

import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;
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
    void saveTransactionSucessfully() throws BadRequestException {
        Set<ConstraintViolation<SaveTransactionDTO>> violations = new HashSet<>();
        when(validator.validate(saveTransactionDTO)).thenReturn(violations);

        transactionGatewayImpl.saveTransaction(saveTransactionDTO);

        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionArgumentCaptor.capture());
    }

    @Test
    void shouldThrowBadRequestExceptionWhenTransactionFails() {
        doThrow(new RuntimeException()).when(transactionRepository).save(any(Transaction.class));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            transactionGatewayImpl.saveTransaction(saveTransactionDTO);
        });

        assertEquals(BadRequestException.class, exception.getClass());}

    @Test
    void throwTransactionExceptionWhenViolationsInSaveTransactionDTOIsNotEmpty() {
        Set<ConstraintViolation<SaveTransactionDTO>> violations = mock(Set.class);
        when(violations.isEmpty()).thenReturn(false);

        when(validator.validate(saveTransactionDTO)).thenReturn(violations);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.saveTransaction(saveTransactionDTO);
        });

        assertEquals(TransactionException.class, exception.getClass());
    }

    @Test
    void deleteTransactionSucessfully() {
        when(transactionRepository.findById(any())).thenReturn(Optional.of(new Transaction(saveTransactionDTO)));
        doNothing().when(transactionRepository).delete(any());

        transactionGatewayImpl.deleteById(1L);

        verify(transactionRepository).delete(any());
    }

    @Test
    void throwTransactionExceptionWhenTransactionIdIsNotFound() {
        when(transactionRepository.findById(any())).thenReturn(Optional.of(new Transaction(saveTransactionDTO)));
        doThrow(new TransactionException()).when(transactionRepository).delete(any());

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.deleteById(1L);
        });

        assertEquals(TransactionException.class, exception.getClass());
    }

    @Test
    void getTransactionsLastMinuteSucessfully() {
        GetStatisticLastMinuteDTO getStatisticLastMinuteDTO = getStatisticLastMinuteDTO();
        when(transactionRepository.findStatisticDataHoraBetween(any(), any())).thenReturn(getStatisticLastMinuteDTO);

        GetStatisticLastMinuteDTO statisticLastMinuteDTO = transactionGatewayImpl.getStatisticLastMinuteDTO();

        verify(transactionRepository).findStatisticDataHoraBetween(any(), any());
        assertNotNull(statisticLastMinuteDTO);
        assertEquals(getStatisticLastMinuteDTO.count(), statisticLastMinuteDTO.count());
        assertEquals(getStatisticLastMinuteDTO.sum(), statisticLastMinuteDTO.sum());
        assertEquals(getStatisticLastMinuteDTO.avg(), statisticLastMinuteDTO.avg());
        assertEquals(getStatisticLastMinuteDTO.min(), statisticLastMinuteDTO.min());
        assertEquals(getStatisticLastMinuteDTO.max(), statisticLastMinuteDTO.max());
    }

    @Test
    void throwTransactionExceptionWhenQueryReturnsError() {
        doThrow(new RuntimeException()).when(transactionRepository).findStatisticDataHoraBetween(any(), any());

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionGatewayImpl.getStatisticLastMinuteDTO();
        });

        assertEquals(TransactionException.class, exception.getClass());
    }


    private SaveTransactionDTO createSaveTransactionDTO() {
        return new SaveTransactionDTO(1F, OffsetDateTime.now());
    }

    private GetStatisticLastMinuteDTO getStatisticLastMinuteDTO() {
        return new GetStatisticLastMinuteDTO(2L, new BigDecimal("44.44"), new BigDecimal("11.60"), new BigDecimal("11.60"), new BigDecimal("15.60"));
    }
}