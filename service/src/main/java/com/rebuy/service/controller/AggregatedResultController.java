package com.rebuy.service.controller;

import com.rebuy.api.scope.LeaderboardApi;
import com.rebuy.api.scope.dto.request.StakeRequest;
import com.rebuy.api.scope.dto.response.ResultResponse;
import com.rebuy.service.converters.StakeConverter;
import com.rebuy.service.entity.GameType;
import com.rebuy.service.entity.Provider;
import com.rebuy.service.entity.Stake;
import com.rebuy.service.service.interfaces.AggregateService;
import com.rebuy.service.service.interfaces.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("v1/results")
@Tag(name = "AggregateResultController")
@CrossOrigin()
@Slf4j
public class AggregatedResultController implements BaseController, LeaderboardApi {

    private final AggregateService aggregateService;
    private final ClientService clientService;

    public AggregatedResultController(AggregateService aggregateService,
                                      ClientService clientService) {
        this.aggregateService = aggregateService;
        this.clientService = clientService;
    }

    @GetMapping("/stake")
    @Operation(summary = "get last year results by provider, gameType and stake if passed")
    public com.rebuy.service.dto.api.response.ResultResponse getAllByStake(@RequestParam Provider provider,
                                                                           @RequestParam GameType gameType,
                                                                           @RequestParam(required = false) Stake stake) {
        return aggregateService.getAllByStake(provider, gameType, stake);
    }

    @GetMapping("/date")
    @Operation(summary = "get results by date from start to end if passed or current date if not, by stake or last year if stake not passed")
    @Parameter(example = "start(yyyy-MM-dd): 2022-09-05, end : 2022-09-05")
    public com.rebuy.service.dto.api.response.ResultResponse getAllByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                                                          @RequestParam Provider provider,
                                                                          @RequestParam GameType gameType,
                                                                          @RequestParam(required = false) Stake stake) {

        return aggregateService.getAllByDate(start, end, provider, gameType, stake);
    }

    @GetMapping("/parse")
    @Operation(summary = "parse results by date from start to end if passed or current date if not")
    @Parameter(example = "start(yyyy-MM-dd): 2022-09-05, end : 2022-09-05")
    public void parseData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                          @RequestParam Provider provider,
                          @RequestParam GameType gameType) {
        if (Objects.isNull(end)) {
            end = LocalDate.now();
        }
        clientService.runDailyDataFlow(start.datesUntil(end).toList(), gameType);
    }

    @Override
    @GetMapping("/parseStake")
    public List<ResultResponse> parseCurrentDataByStake(StakeRequest stake) {
        log.info("request to /parseStake with stake param " + stake.name());
        return clientService.runDailyDataFlow(StakeConverter.toStake(stake), GameType.SHORT_DECK);
    }

}
