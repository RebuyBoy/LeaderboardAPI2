package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.api.response.ResultResponse;
import com.rebuy.service.entity.GameType;
import com.rebuy.service.entity.Provider;
import com.rebuy.service.entity.Stake;

import java.time.LocalDate;

public interface AggregateService {

    ResultResponse getAllByStake(Provider provider, GameType gameType, Stake stake);

    ResultResponse getAllByDate(LocalDate start, LocalDate end, Provider provider, GameType gameType, Stake stake);

}
