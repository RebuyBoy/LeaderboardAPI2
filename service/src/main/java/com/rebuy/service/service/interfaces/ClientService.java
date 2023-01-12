package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.api.ResultTelegram;
import com.rebuy.service.entity.GameType;
import com.rebuy.service.entity.Stake;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {

    void runDailyDataFlow(List<LocalDate> dates, GameType gameType);

    void runDailyDataFlow();

    void runDailyDataFlow(LocalDate date,GameType gameType);

    List<ResultTelegram> runDailyDataFlow(Stake stake, GameType gameType);
}
