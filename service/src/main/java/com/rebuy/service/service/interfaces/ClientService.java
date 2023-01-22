package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.api.response.ResultResponse;
import com.rebuy.service.entity.Stake;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {

    List<ResultResponse> parseResults(LocalDate date, Stake stake, boolean saveToDB);

}
