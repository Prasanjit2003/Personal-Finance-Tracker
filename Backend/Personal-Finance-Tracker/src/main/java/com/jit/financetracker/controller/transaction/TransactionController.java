package com.jit.financetracker.controller.transaction;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jit.financetracker.dto.response.TransactionResponseDto;
import com.jit.financetracker.enums.TransactionType;
import com.jit.financetracker.service.TransactionService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // PAGINATED + FILTERED TRANSACTIONS
    @Hidden
    @GetMapping
    public ResponseEntity<Page<TransactionResponseDto>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                transactionService.getTransactions(
                        page,
                        size,
                        sortBy,
                        direction,
                        type,
                        startDate,
                        endDate
                )
        );
    }
}
