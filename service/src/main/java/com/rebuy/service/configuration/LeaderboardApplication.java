package com.rebuy.service.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = "com.rebuy.service.repository")
@EntityScan(basePackages = {"com.rebuy.service.entity", "com.rebuy.service.converters"})
@ComponentScan(basePackages = "com.rebuy.service")
@EnableScheduling
@EnableCaching
public class LeaderboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaderboardApplication.class, args);
    }

}
