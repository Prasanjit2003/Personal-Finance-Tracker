package com.jit.financetracker.dashboard.dto;

import java.math.BigDecimal;

public class MonthlyComparisonDto {

    private String month; 
    private BigDecimal income;
    private BigDecimal expense;

    public MonthlyComparisonDto(
            String month,
            BigDecimal income,
            BigDecimal expense
    ) {
        this.month = month;
        this.income = income;
        this.expense = expense;
    }

    public String getMonth() {
        return month;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public BigDecimal getExpense() {
        return expense;
    }
}
