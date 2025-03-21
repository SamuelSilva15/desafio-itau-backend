package com.br.transactions.usecase.statistic;

import com.br.transactions.core.domain.transaction.GetStatisticLastMinuteDTO;

public interface GetStatisticUsecase {
    GetStatisticLastMinuteDTO execute();
}
