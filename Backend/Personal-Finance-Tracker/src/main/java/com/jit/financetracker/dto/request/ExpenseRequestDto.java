package com.jit.financetracker.dto.request;

import com.jit.financetracker.enums.ExpenseCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor   
@AllArgsConstructor
public class ExpenseRequestDto {

    private BigDecimal amount;
    private ExpenseCategory category;
    private String description;
    private LocalDate expenseDate;
}
