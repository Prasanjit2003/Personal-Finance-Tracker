package com.jit.financetracker.controller.budget;

import com.jit.financetracker.dto.request.BudgetRequestDto;
import com.jit.financetracker.dto.response.BudgetResponseDto;
import com.jit.financetracker.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    // CREATE / UPDATE 
    @PostMapping
    public ResponseEntity<BudgetResponseDto> saveBudget(
            @RequestBody BudgetRequestDto request
    ) {
        return new ResponseEntity<>(
                budgetService.saveBudget(request),
                HttpStatus.CREATED
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<BudgetResponseDto>> getBudgets() {
        return ResponseEntity.ok(budgetService.getMyBudgets());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
