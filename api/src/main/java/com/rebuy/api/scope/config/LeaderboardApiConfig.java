package com.rebuy.api.scope.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.rebuy.api.scope.feignclient")
@ComponentScan({"com.rebuy.api.scope"})
public class LeaderboardApiConfig {
}
