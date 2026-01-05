package com.jit.financetracker.controller.expense;

import com.jit.financetracker.dto.request.ExpenseRequestDto;
import com.jit.financetracker.dto.response.ExpenseResponseDto;
import com.jit.financetracker.enums.ExpenseCategory;
import com.jit.financetracker.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // ADD
    @PostMapping
    public ResponseEntity<ExpenseResponseDto> addExpense(
            @RequestBody ExpenseRequestDto request
    ) {
        return new ResponseEntity<>(
                expenseService.addExpense(request),
                HttpStatus.CREATED
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getExpenses() {
        return ResponseEntity.ok(expenseService.getMyExpenses());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    // PAGINATION + FILTERING 
    @GetMapping("/page")
    public ResponseEntity<Page<ExpenseResponseDto>> getExpensesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) ExpenseCategory category,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                expenseService.getExpensesPaged(
                        page,
                        size,
                        sortBy,
                        direction,
                        category,
                        startDate,
                        endDate
                )
        );
    }
}
