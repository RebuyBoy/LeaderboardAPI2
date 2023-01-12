package com.rebuy.api.scope.feignclient;

import com.rebuy.api.scope.LeaderboardApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "leaderboard", url = "localhost:8081")
public interface LeaderboardApiClient extends LeaderboardApi{
}
