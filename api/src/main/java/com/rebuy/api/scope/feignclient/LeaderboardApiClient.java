package com.rebuy.api.scope.feignclient;

import com.rebuy.api.scope.dto.request.Stake;
import com.rebuy.api.scope.dto.response.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Leaderboard", url = "leaderboard-api:8081" , path = "/leaderboard/v1/results")
public interface LeaderboardApiClient {

    @GetMapping("/current")
    List<ResultResponse> parseCurrentDataByStake(@RequestParam Stake stake);
}
