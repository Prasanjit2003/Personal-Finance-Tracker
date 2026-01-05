package com.jit.financetracker.dashboard.dto;

import java.math.BigDecimal;

public class DashboardSummaryDto {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private BigDecimal currentMonthExpense;
    
    public DashboardSummaryDto(
    		BigDecimal totalIncome,
    	    BigDecimal totalExpense,
    	    BigDecimal balance,
    	    BigDecimal currentMonthExpense
    ) {
    	this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.currentMonthExpense = currentMonthExpense;
    	
    }

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal getCurrentMonthExpense() {
		return currentMonthExpense;
	}
    
}
