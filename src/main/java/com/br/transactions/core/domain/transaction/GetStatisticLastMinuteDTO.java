package com.br.transactions.core.domain.transaction;

public record GetStatisticLastMinuteDTO(Long count, double sum, double avg, float min, float max) {
    public GetStatisticLastMinuteDTO {
    }
}
