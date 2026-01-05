package com.jit.financetracker.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ForecastPointDto {

    private String period;
    private BigDecimal predictedAmount;
}
