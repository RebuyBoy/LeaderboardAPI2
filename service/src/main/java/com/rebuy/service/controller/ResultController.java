package com.rebuy.service.controller;

import com.rebuy.service.dto.api.AggregatedResult;
import com.rebuy.service.dto.api.PlaceAndPoints;
import com.rebuy.service.dto.api.response.ResultResponse;
import com.rebuy.service.entity.Stake;
import com.rebuy.service.service.interfaces.AggregateService;
import com.rebuy.service.service.interfaces.ClientService;
import com.rebuy.service.util.ZonedLocalDateSupplier;
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
import java.util.List;

@RestController
@RequestMapping("v1/results")
@Tag(name = "AggregateResultController")
@CrossOrigin()
@Slf4j
public class ResultController implements BaseController {

    private final AggregateService aggregateService;
    private final ClientService clientService;

    public ResultController(AggregateService aggregateService,
                            ClientService clientService) {
        this.aggregateService = aggregateService;
        this.clientService = clientService;
    }

    @GetMapping("/aggregated")
    @Operation(summary = "get results by date from start to end if passed or current date if not, by stake or all stakes")
    @Parameter(example = "from(yyyy-MM-dd): 2022-09-05, to : 2022-09-05")
    public List<AggregatedResult> getResultsByStake(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam(required = false) Stake stake) {

        return aggregateService.getResults(from, to, stake);
    }

    @GetMapping("/averages")
    @Operation(summary = "returns average points for first 3 ranks ")
    @Parameter(example = "from(yyyy-MM-dd): 2022-09-05, to : 2022-09-05")
    public List<PlaceAndPoints> getAveragePoints(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam() Stake stake) {

        return aggregateService.getAveragePoints(from, to, stake);
    }

    @GetMapping("/current")
    public List<ResultResponse> getCurrentDataByStake(@RequestParam() Stake stake) {
        log.info("request to /current with stake {}", stake.name());
        LocalDate currentDate = ZonedLocalDateSupplier.localDateNowGMTMinus8();

        return clientService.getResults(currentDate, stake);
    }

}
