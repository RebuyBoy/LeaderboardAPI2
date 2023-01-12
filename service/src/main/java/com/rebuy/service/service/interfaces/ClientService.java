package com.rebuy.service.service.interfaces;

import com.rebuy.api.scope.dto.response.ResultResponse;
import com.rebuy.service.entity.GameType;
import com.rebuy.service.entity.Stake;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {

    void runDailyDataFlow(List<LocalDate> dates, GameType gameType);

    void runDailyDataFlow();

    void runDailyDataFlow(LocalDate date,GameType gameType);

    List<ResultResponse> runDailyDataFlow(Stake stake, GameType gameType);
}
