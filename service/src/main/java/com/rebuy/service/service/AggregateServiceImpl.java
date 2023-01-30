package com.rebuy.service.service;

import com.rebuy.service.dto.api.AggregatedResult;
import com.rebuy.service.dto.api.response.PlayerResponse;
import com.rebuy.service.entity.Player;
import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;
import com.rebuy.service.service.interfaces.AggregateService;
import com.rebuy.service.service.interfaces.ResultService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AggregateServiceImpl implements AggregateService {

    private final ResultService resultService;

    public AggregateServiceImpl(ResultService resultService) {
        this.resultService = resultService;
    }

    private List<AggregatedResult> aggregate(List<Result> results) {
        Map<Player, AggregatedResult> playerAggregatedResult = new HashMap<>();

        for (Result result : results) {
            Player player = result.getPlayer();
            playerAggregatedResult.compute(player, (p, aggregatedResult) -> {
                if (aggregatedResult == null) {
                    return createAggregatedResult(player, result);
                }
                return summarizeResults(aggregatedResult, result);
            });
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
                .name(player.getName()).country(player.getCountry())
                .build();
    }

    @Override
    public List<AggregatedResult> getResults(LocalDate from, LocalDate to, Stake stake) {
        return aggregate(resultService.get(from, to, stake));
    }

}
