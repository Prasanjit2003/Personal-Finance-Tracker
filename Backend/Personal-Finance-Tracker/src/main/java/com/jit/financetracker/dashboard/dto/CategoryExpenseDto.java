package com.jit.financetracker.dashboard.dto;

import java.math.BigDecimal;

public class CategoryExpenseDto {

    private String category;
    private BigDecimal amount;

    public CategoryExpenseDto(
            String category,
            BigDecimal amount
    ) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
