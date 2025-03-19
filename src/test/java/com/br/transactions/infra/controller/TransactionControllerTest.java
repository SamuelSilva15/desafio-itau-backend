package com.br.transactions.infra.controller;

import com.br.transactions.core.domain.transaction.SaveTransactionDTO;
import com.br.transactions.infra.entity.transaction.Transaction;
import com.br.transactions.infra.repository.transaction.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void saveTransactionSucessfully() throws Exception {
        SaveTransactionDTO saveTransactionDTO = new SaveTransactionDTO(1F, OffsetDateTime.now());

        String transactionJson = objectMapper.writeValueAsString(saveTransactionDTO);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void throwUnprocessableEntityWhenValueIsNull() throws Exception {
        SaveTransactionDTO saveTransactionDTO = new SaveTransactionDTO(null, OffsetDateTime.now());
        String transactionJson = objectMapper.writeValueAsString(saveTransactionDTO);


        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void throwUnprocessableEntityWhenValueIsNegative() throws Exception {
        SaveTransactionDTO saveTransactionDTO = new SaveTransactionDTO(-10.5F, OffsetDateTime.now());
        String transactionJson = objectMapper.writeValueAsString(saveTransactionDTO);


        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void throwUnprocessableEntityWhenDateAnHourIsNull() throws Exception {
        SaveTransactionDTO saveTransactionDTO = new SaveTransactionDTO(1F, null);

        String transactionJson = objectMapper.writeValueAsString(saveTransactionDTO);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void throwUnprocessableEntityWhenDateAnHourIsFuture() throws Exception {
        SaveTransactionDTO saveTransactionDTO = new SaveTransactionDTO(1F, OffsetDateTime.now().plus(5, ChronoUnit.DAYS));

        String transactionJson = objectMapper.writeValueAsString(saveTransactionDTO);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void throwBadRequestWhenJsonIsInvalid() throws Exception {
        String invalidJson = "{ \"valor\": 10, \"dataHora\": }";

        String transactionJson = objectMapper.writeValueAsString(invalidJson);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void deleteTransactionByIdSucessfully() throws Exception {
        createTransaction();
        mockMvc.perform(delete("/transaction/{transactionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void throwBadRequestWhenTransactionIdDontExists() throws Exception {
        mockMvc.perform(delete("/transaction/{transactionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }


    private void createTransaction() {
        SaveTransactionDTO saveTransactionDTO = new SaveTransactionDTO(1F, OffsetDateTime.now());
        transactionRepository.save(new Transaction(saveTransactionDTO));
    }
}