package com.rebuy.service.converters;

import com.rebuy.api.scope.dto.request.StakeRequest;
import com.rebuy.service.entity.Stake;

public class StakeConverter {
    public static Stake toStake(StakeRequest stakeRequest) {
        return Stake.valueOf(stakeRequest.name());
    }
}
