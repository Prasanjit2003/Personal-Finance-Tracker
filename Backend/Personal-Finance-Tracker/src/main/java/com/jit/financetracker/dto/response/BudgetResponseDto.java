package com.jit.financetracker.dto.response;

import com.jit.financetracker.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BudgetResponseDto {

    private Long id;
    private ExpenseCategory category;
    private String month;
    private BigDecimal monthlyLimit;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;
}
