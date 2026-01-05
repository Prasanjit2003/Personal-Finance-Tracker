package com.jit.financetracker.dashboard.dto;

import java.math.BigDecimal;

public class YoYGrowthDto {

    private int year;
    private BigDecimal incomeGrowthPercent;
    private BigDecimal expenseGrowthPercent;
    private BigDecimal balanceGrowthPercent;
    
    public YoYGrowthDto(
    		int year,
    	    BigDecimal incomeGrowthPercent,
    	    BigDecimal expenseGrowthPercent,
    	    BigDecimal balanceGrowthPercent
    ) {
    	this.year = year;
    	this.incomeGrowthPercent = incomeGrowthPercent;
    	this.expenseGrowthPercent = expenseGrowthPercent;
    	this.balanceGrowthPercent = balanceGrowthPercent;
    }

	public int getYear() {
		return year;
	}

	public BigDecimal getIncomeGrowthPercent() {
		return incomeGrowthPercent;
	}

	public BigDecimal getExpenseGrowthPercent() {
		return expenseGrowthPercent;
	}

	public BigDecimal getBalanceGrowthPercent() {
		return balanceGrowthPercent;
	}
    
}
