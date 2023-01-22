package com.rebuy.service.service.interfaces;

import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;

import java.time.LocalDate;
import java.util.List;

public interface ResultService {
    List<Result> get(LocalDate from, LocalDate to, Stake stake);

//    List<Result> getByDateFrom(LocalDate from);
//
//    List<Result> getByDateBetween(LocalDate from, LocalDate to);
//
//    List<Result> getAllByStake(Provider provider, GameType gameType, Stake stake);
//
//    List<Result> getAllByDate(LocalDate start, LocalDate end, Provider provider, GameType gameType, Stake stake);
//
    Result saveIfNotExists(Result result);
//
//    List<Provider> getAllProviders();
//
//    LocalDate getLastUpdateByProvider(Provider provider);
//
//    List<GameType> getGameTypesDataByProvider(Provider provider);
//
//    List<Stake> getStakesByProviderAndGameType(Provider provider, GameType gameType);

}
