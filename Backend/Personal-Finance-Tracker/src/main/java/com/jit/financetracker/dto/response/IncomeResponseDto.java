package com.jit.financetracker.dto.response;

import com.jit.financetracker.enums.IncomeFrequency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class IncomeResponseDto {

    private Long id;
    private BigDecimal amount;
    private String source;
    private IncomeFrequency frequency;
    private LocalDate incomeDate;
}
