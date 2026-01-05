package com.jit.financetracker.forecast.controller;

import com.jit.financetracker.enums.TransactionType;
import com.jit.financetracker.forecast.dto.ForecastPointDto;
import com.jit.financetracker.forecast.dto.TrendSummaryDto;
import com.jit.financetracker.forecast.service.ForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    // FORECAST
    @GetMapping
    public ResponseEntity<List<ForecastPointDto>> forecast(
            @RequestParam TransactionType type,
            @RequestParam(defaultValue = "3") int months
    ) {
        return ResponseEntity.ok(
                forecastService.forecast(type, months)
        );
    }

    // TREND
    @GetMapping("/trend")
    public ResponseEntity<TrendSummaryDto> trend(
            @RequestParam TransactionType type
    ) {
        return ResponseEntity.ok(
                forecastService.trend(type)
        );
    }
}
