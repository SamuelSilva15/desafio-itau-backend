package com.br.transactions.core.domain.transaction;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record SaveTransactionDTO(@NotNull(message = "Valor precisa ser preenchido.")
                                 @Positive(message = "Valor não pode ser negativo. Deve igual ou maior que zero.") Float valor,
                                 @NotNull(message = "Data e hora precisam ser preenchidos.")
                                 @PastOrPresent(message = "Transação não pode ser feita no futuro.") OffsetDateTime dataHora) {}