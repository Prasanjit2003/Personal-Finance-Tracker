package com.jit.financetracker.dto.request;

import com.jit.financetracker.enums.ExpenseCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor   
@AllArgsConstructor
public class BudgetRequestDto {

    private ExpenseCategory category;
    private BigDecimal monthlyLimit;
    private String month;
}
