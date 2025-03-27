package com.br.transactions.application.usecaseimpl.statistic;

import com.br.transactions.application.gateway.transaction.TransactionGateway;
import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;
import com.br.transactions.usecase.statistic.GetStatisticUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetStatisticUsecaseImpl implements GetStatisticUsecase {

    private final TransactionGateway transactionGateway;

    @Override
    public GetStatisticLastMinuteDTO execute(Long time) {
        return transactionGateway.getStatisticLastMinuteDTO(time);
    }
}