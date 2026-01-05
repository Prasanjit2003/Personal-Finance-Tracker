package com.jit.financetracker.controller.income;

import com.jit.financetracker.dto.request.IncomeRequestDto;
import com.jit.financetracker.dto.response.IncomeResponseDto;
import com.jit.financetracker.service.IncomeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    // ADD
    @PostMapping
    public ResponseEntity<IncomeResponseDto> addIncome(
            @RequestBody IncomeRequestDto request
    ) {
        return new ResponseEntity<>(
                incomeService.addIncome(request),
                HttpStatus.CREATED
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<IncomeResponseDto>> getIncomes() {
        return ResponseEntity.ok(incomeService.getMyIncome());
    }

    // DELETE 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }

    // PAGINATION + FILTERING
    @GetMapping("/page")
    public ResponseEntity<Page<IncomeResponseDto>> getIncomesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incomeDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                incomeService.getIncomesPaged(
                        page,
                        size,
                        sortBy,
                        direction,
                        startDate,
                        endDate
                )
        );
    }
}
