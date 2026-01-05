package com.jit.financetracker.dashboard.projection;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface YearlyMonthlyProjection {

    String getMonth();        
    BigDecimal getIncome();
    BigDecimal getExpense();
}
