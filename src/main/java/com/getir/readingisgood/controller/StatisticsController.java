package com.getir.readingisgood.controller;

import com.getir.readingisgood.response.StatisticsResponse;
import com.getir.readingisgood.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<StatisticsResponse>> getMonthlyOrderStatistics(@PathVariable Long customerId) {
        return new ResponseEntity<>(statisticsService.getMonthlyOrderStatistics(customerId), HttpStatus.OK);
    }
}
