package com.rebuy.service.controller;

import com.rebuy.service.dto.api.DateAndCount;
import com.rebuy.service.service.interfaces.ClientService;
import com.rebuy.service.service.interfaces.ResultService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("v1/results/admin")
@Tag(name = "AdminController")
@RestController
@CrossOrigin()
@Slf4j
public class AdminController implements BaseController {

    private final ResultService resultService;
    private final ClientService clientService;

    public AdminController(ResultService resultService,
                           ClientService clientService) {
        this.resultService = resultService;
        this.clientService = clientService;
    }

    @GetMapping("/info")
    List<DateAndCount> getResultNumber() {
        return resultService.getResultNumber();
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

    @DeleteMapping("/deleteDay")
    public void deleteByDay(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        resultService.deleteByDate(date);
    }
}
