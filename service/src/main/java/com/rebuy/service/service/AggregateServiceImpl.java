package com.rebuy.service.service;

import com.rebuy.service.constants.Constants;
import com.rebuy.service.dto.api.AggregatedResult;
import com.rebuy.service.dto.api.response.PlayerResponse;
import com.rebuy.service.dto.api.response.ProviderResponse;
import com.rebuy.service.dto.api.response.ResultResponse;
import com.rebuy.service.entity.GameType;
import com.rebuy.service.entity.Player;
import com.rebuy.service.entity.Provider;
import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;
import com.rebuy.service.service.interfaces.AggregateService;
import com.rebuy.service.service.interfaces.ResultService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AggregateServiceImpl implements AggregateService {

    private final ResultService resultService;

    public AggregateServiceImpl(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    public ResultResponse getAllByStake(Provider provider, GameType gameType, Stake stake) {
        List<AggregatedResult> aggregate = aggregate(resultService.getAllByStake(provider, gameType, stake));
        return new ResultResponse(new ProviderResponse(provider.name(), provider.getDescription(), provider.getCurrency()), aggregate);
    }

    @Override
    public ResultResponse getAllByDate(LocalDate start, LocalDate end, Provider provider, GameType gameType, Stake stake) {
        if (end == null) {
            end = LocalDate.now((ZoneId.of(Constants.GMT_MINUS_8)));
        }
        List<AggregatedResult> aggregatedResultsByDateBetween = aggregate(resultService.getAllByDate(start, end, provider, gameType, stake));
        return new ResultResponse(new ProviderResponse(provider.name(), provider.getDescription(), provider.getCurrency()), aggregatedResultsByDateBetween);
    }

    private List<AggregatedResult> aggregate(List<Result> results) {
        Map<Player, AggregatedResult> playerAggregatedResult = new HashMap<>();

        for (Result result : results) {
            Player player = result.getPlayer();
            playerAggregatedResult.computeIfPresent(player, (p, aggregatedResult) -> summarizeResults(aggregatedResult, result));
            playerAggregatedResult.putIfAbsent(player, createAggregatedResult(player, result));
        }
        return playerAggregatedResult.values().stream().sorted().toList();
    }

    private AggregatedResult summarizeResults(AggregatedResult aggregatedResult, Result result) {
        return new AggregatedResult.Builder()
                .player(aggregatedResult.getPlayer())
                .totalPoints(aggregatedResult.getTotalPoints().add(result.getPoints()))
                .totalPrize(aggregatedResult.getTotalPrize().add(result.getPrize()))
                .build();
    }

    private AggregatedResult createAggregatedResult(Player player, Result result) {
        return new AggregatedResult.Builder()
                .player(toPlayerResponse(player))
                .totalPoints(result.getPoints())
                .totalPrize(result.getPrize())
                .build();
    }

    private PlayerResponse toPlayerResponse(Player player) {
        return new PlayerResponse.Builder()
                .name(player.getName())
                .country(player.getCountry())
                .build();
    }

}
