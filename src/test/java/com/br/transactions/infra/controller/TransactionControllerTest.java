package com.br.transactions.infra.controller;

import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;
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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        SaveTransactionDTO saveTransactionDTO = createTransaction();

        String transactionJson = objectMapper.writeValueAsString(saveTransactionDTO);

        mockMvc.perform(post("/transacao")
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


        mockMvc.perform(post("/transacao")
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


        mockMvc.perform(post("/transacao")
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

        mockMvc.perform(post("/transacao")
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

        mockMvc.perform(post("/transacao")
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

        mockMvc.perform(post("/transacao")
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
        mockMvc.perform(delete("/transacao/{transactionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void throwBadRequestWhenTransactionIdDontExists() throws Exception {
        mockMvc.perform(delete("/transacao/{transactionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void getTransactionsLastMinuteSucessfully() throws Exception {
        String response = mockMvc.perform(get("/transacao/estatistica")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        GetStatisticLastMinuteDTO statisticLastMinuteDTO = objectMapper.readValue(response, GetStatisticLastMinuteDTO.class);
        assertNotNull(statisticLastMinuteDTO);
        assertEquals(2L, statisticLastMinuteDTO.count());
        assertEquals(new BigDecimal("29.40"), statisticLastMinuteDTO.sum());
        assertEquals(new BigDecimal("14.70"), statisticLastMinuteDTO.avg());
        assertEquals(new BigDecimal("14.70"), statisticLastMinuteDTO.min());
        assertEquals(new BigDecimal("14.70"), statisticLastMinuteDTO.max());
    }

    @Test
    void throwBadRequestWhenQueryReturnsError() throws Exception {
        mockMvc.perform(get("/transacao/estatistica")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadGateway())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private SaveTransactionDTO createTransaction() {
        SaveTransactionDTO saveTransactionDTO = new SaveTransactionDTO(14.70F, OffsetDateTime.now());
        transactionRepository.save(new Transaction(saveTransactionDTO));
        return saveTransactionDTO;
    }
}