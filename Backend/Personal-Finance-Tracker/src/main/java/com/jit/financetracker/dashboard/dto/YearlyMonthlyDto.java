package com.jit.financetracker.dashboard.dto;

import java.math.BigDecimal;

public class YearlyMonthlyDto {

    private String month; // YYYY-MM
    private BigDecimal income;
    private BigDecimal expense;

    public YearlyMonthlyDto(
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
