package com.rebuy.service.controller;

import com.rebuy.service.dto.api.AggregatedResult;
import com.rebuy.service.dto.api.response.ResultResponse;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.rebuy.service.constants.Constants.GMT_MINUS_8;

@RestController
@RequestMapping("v1/results")
@Tag(name = "AggregateResultController")
@CrossOrigin()
@Slf4j
public class AggregatedResultController implements BaseController {

    private final AggregateService aggregateService;
    private final ClientService clientService;

    public AggregatedResultController(AggregateService aggregateService,
                                      ClientService clientService) {
        this.aggregateService = aggregateService;
        this.clientService = clientService;
    }

    @GetMapping("/aggregated")
    @Operation(summary = "get results by date from start to end if passed or current date if not, by stake or all stakes")
    @Parameter(example = "start(yyyy-MM-dd): 2022-09-05, end : 2022-09-05")
    public List<AggregatedResult> getResults(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @RequestParam(required = false) Stake stake) {

        return aggregateService.getResults(start, end, stake);
    }

    @GetMapping("/current")
    public List<ResultResponse> getCurrentDataByStake(@RequestParam() Stake stake) {
        log.info("request to /current with stake {}", stake.name());
        LocalDate currentDate = ZonedDateTime
                .now(ZoneId.of(GMT_MINUS_8))
                .toLocalDate();

        return clientService.getResults(currentDate, stake);
    }

    @GetMapping("/parseDay")
    public void parseDay(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.info("request to /parse day with date {}", date);
        clientService.getAndSaveResults(date);
    }

    @GetMapping("/parseDays")
    public void parseDays(
            @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("request to /parse days from {} to {}", start, end);
        clientService.getAndSaveResults(start, end);
    }

}
