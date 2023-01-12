package com.rebuy.service.converters;

import com.rebuy.service.dto.client.gg.GGResultResponse;
import com.rebuy.service.entity.Country;
import com.rebuy.service.entity.DateLB;
import com.rebuy.service.entity.GameType;
import com.rebuy.service.entity.Player;
import com.rebuy.service.entity.Provider;
import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Stream;

@Component
public class ResultResponseConverter {

    public Result convert(GGResultResponse resultDTO,
                          LocalDate date,
                          String stakeStr,
                          GameType gameType,
                          Provider provider) {
        return new Result.Builder()
                .point(resultDTO.getPoints())
                .prize(resultDTO.getPrize())
                .rank(resultDTO.getRank())
                .player(getPlayer(resultDTO))
                .date(new DateLB(date))
                .stake(convertToEntityAttributeByStakeDescription(stakeStr))
                .gameType(gameType)
                .provider(provider)
                .build();
    }

    private Player getPlayer(GGResultResponse resultDTO) {
        return new Player.Builder()
                .name(resultDTO.getName())
                .country(new Country(resultDTO.getCountryCode()))
                .build();
    }

    public Stake convertToEntityAttributeByStakeDescription(String stakeDescription) {
        return Stream.of(Stake.values())
                .filter(stake -> stake.getDescription().equals(stakeDescription))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Stake not found by stake stakeDescription: " + stakeDescription));
    }

}
