package com.br.transactions.core.domain.transaction;

import java.math.BigDecimal;

public record GetStatisticLastMinuteDTO(Long count, BigDecimal sum, BigDecimal avg, BigDecimal min, BigDecimal max) {
}
