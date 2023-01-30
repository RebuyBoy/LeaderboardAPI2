package com.rebuy.service.controller;

import com.rebuy.service.dto.api.DateAndCount;
import com.rebuy.service.service.interfaces.ResultService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("v1/results")
@Tag(name = "AdminController")
@RestController
@CrossOrigin()
public class AdminController implements BaseController {

    private final ResultService resultService;

    public AdminController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/number")
    List<DateAndCount> getResultNumber() {
        return resultService.getResultNumber();
    }

}
