package com.jit.financetracker.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrendSummaryDto {

    private String type;  
    private String trend; 
    private double slope;
}
