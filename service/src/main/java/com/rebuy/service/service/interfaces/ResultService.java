package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.api.DateAndCount;
import com.rebuy.service.dto.api.PlaceAndPoints;
import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;

import java.time.LocalDate;
import java.util.List;

public interface ResultService {

    List<Result> get(LocalDate from, LocalDate to, Stake stake);

    Result saveIfNotExists(Result result);

    void deleteByDate(LocalDate date);

    List<DateAndCount> getResultNumber();

    List<PlaceAndPoints> getAverages(LocalDate from, LocalDate to, Stake stake);
}
