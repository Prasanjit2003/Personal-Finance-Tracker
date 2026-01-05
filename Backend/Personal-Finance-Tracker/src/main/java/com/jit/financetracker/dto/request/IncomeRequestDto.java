package com.jit.financetracker.dto.request;

import com.jit.financetracker.enums.IncomeFrequency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor   
@AllArgsConstructor
public class IncomeRequestDto {

    private BigDecimal amount;
    private String source;
    private IncomeFrequency frequency;
    private LocalDate incomeDate;
}
