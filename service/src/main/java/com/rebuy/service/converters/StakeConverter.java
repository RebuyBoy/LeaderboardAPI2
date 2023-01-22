package com.rebuy.service.converters;

import com.rebuy.api.scope.dto.request.Stake;

import java.util.Map;
import java.util.Set;

public class StakeConverter {

    private static final Set<String> SUITABLE_STAKES_SHORT_DECK = Set.of(
            "$10.00",
            "$5.00",
            "$2.00",
            "$1.00",
            "$0.50",
            "$0.25",
            "$0.10"
    );

    private static final Map<String, String> STAKES_TO_PART_OF_URL_SHORT_DECK = Map.of(
            "$10.00", "10",
            "$5.00", "15",
            "$2.00", "20",
            "$1.00", "25",
            "$0.50", "35",
            "$0.25", "50",
            "$0.10", "60",
            "$0.05", "70",
            "$0.02", "120"
    );

    private StakeConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static com.rebuy.service.entity.Stake toStake(Stake stakeRequest) {
        return com.rebuy.service.entity.Stake.valueOf(stakeRequest.name());
    }

    public static boolean isSuitable(String stake){
        return SUITABLE_STAKES_SHORT_DECK.contains(stake);
    }

    public static String toUrlPart(String stake){
        if(STAKES_TO_PART_OF_URL_SHORT_DECK.containsKey(stake)){
            return STAKES_TO_PART_OF_URL_SHORT_DECK.get(stake);
        }
        throw new IllegalArgumentException("wrong stake: " + stake);
    }


}
