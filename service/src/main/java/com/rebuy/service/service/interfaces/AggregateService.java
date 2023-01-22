package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.api.AggregatedResult;
import com.rebuy.service.dto.api.response.ResultResponse;
import com.rebuy.service.entity.Stake;

import java.time.LocalDate;
import java.util.List;

public interface AggregateService {

//    ResultResponse getAllByStake(Stake stake);

    List<AggregatedResult> getResults(LocalDate from, LocalDate to, Stake stake);

}
