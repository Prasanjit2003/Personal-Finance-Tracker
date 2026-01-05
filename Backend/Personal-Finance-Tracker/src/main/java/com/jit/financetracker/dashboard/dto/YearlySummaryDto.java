package com.jit.financetracker.dashboard.dto;

import java.math.BigDecimal;

public class YearlySummaryDto {

    private int year;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    
    public YearlySummaryDto(
    		int year,
    		BigDecimal totalIncome,
    	    BigDecimal totalExpense,
    	    BigDecimal balance
   ) {
    	this.year = year;
    	this.totalIncome = totalIncome;
    	this.totalExpense = totalExpense;
    	this.balance = balance;
    	
    }

	public int getYear() {
		return year;
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
    
}
