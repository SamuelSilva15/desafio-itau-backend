package com.br.transactions.infra.service;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.infra.repository.transaction.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionGatewayImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionGatewayImpl transactionGatewayImpl;


    @BeforeEach
    void setup() {

    }

    @Test
    void saveTransaction() {
        SaveTransactionDTO dto = createSaveTransactionDTO();
        transactionGatewayImpl.saveTransaction(dto);
    }

    private SaveTransactionDTO createSaveTransactionDTO() {
        return new SaveTransactionDTO(1F, OffsetDateTime.now());
    }
}