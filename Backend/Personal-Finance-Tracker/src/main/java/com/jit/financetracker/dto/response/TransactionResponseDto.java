package com.jit.financetracker.dto.response;

import com.jit.financetracker.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponseDto {

    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private String category;
    private LocalDateTime transactionDate;
}
