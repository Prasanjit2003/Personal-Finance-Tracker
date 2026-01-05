package com.jit.financetracker.dashboard.dto;

import java.math.BigDecimal;

public class YearlyComparisonDto {

    private Integer year;
    private BigDecimal income;
    private BigDecimal expense;

    public YearlyComparisonDto(
            Integer year,
            BigDecimal income,
            BigDecimal expense
    ) {
        this.year = year;
        this.income = income;
        this.expense = expense;
    }

    public Integer getYear() {
        return year;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public BigDecimal getExpense() {
        return expense;
    }
}
