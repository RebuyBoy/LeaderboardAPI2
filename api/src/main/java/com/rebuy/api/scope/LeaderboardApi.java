package com.rebuy.api.scope;

import com.rebuy.api.scope.dto.request.StakeRequest;
import com.rebuy.api.scope.dto.response.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LeaderboardApi {
    @GetMapping("/parseStake")
    List<ResultResponse> parseCurrentDataByStake(@RequestParam StakeRequest stake);
}
