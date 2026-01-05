package com.jit.financetracker.dashboard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jit.financetracker.dashboard.dto.CategoryExpenseDto;
import com.jit.financetracker.dashboard.dto.DashboardSummaryDto;
import com.jit.financetracker.dashboard.dto.MonthlyComparisonDto;
import com.jit.financetracker.dashboard.dto.YearlyComparisonDto;
import com.jit.financetracker.dashboard.dto.YearlyMonthlyDto;
import com.jit.financetracker.dashboard.dto.YearlySummaryDto;
import com.jit.financetracker.dashboard.dto.YoYGrowthDto;
import com.jit.financetracker.dashboard.service.DashboardService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Dashboard Analytics", description = "Charts, summaries and analytics APIs")
@RestController
@Hidden
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // SUMMARY
    @Operation(summary = "Dashboard summary")
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    // PIE CHART
    @Operation(summary = "Expense by category")
    @GetMapping("/expense-by-category")
    public ResponseEntity<List<CategoryExpenseDto>> expenseByCategory() {
        return ResponseEntity.ok(dashboardService.expenseByCategory());
    }

    // BAR / LINE CHART 
    @Operation(summary = "Monthly income vs expense comparison")
    @GetMapping("/monthly-comparison")
    public ResponseEntity<List<MonthlyComparisonDto>> monthlyComparison() {
        return ResponseEntity.ok(dashboardService.monthlyComparison());
    }

    // YEARLY SUMMARY
    @Operation(summary = "Yearly income, expense and balance summary")
    @GetMapping("/yearly-summary")
    public ResponseEntity<YearlySummaryDto> yearlySummary(
            @RequestParam int year
    ) {
        return ResponseEntity.ok(dashboardService.yearlySummary(year));
    }

    // YEARLY MONTHLY CHART
    @Operation(summary = "Monthly breakdown for a given year")
    @GetMapping("/yearly-monthly")
    public ResponseEntity<List<YearlyMonthlyDto>> yearlyMonthly(
            @RequestParam int year
    ) {
        return ResponseEntity.ok(
                dashboardService.yearlyMonthlyBreakdown(year)
        );
    }

    // YoY GROWTH
    @Operation(summary = "Year-over-year growth analysis")
    @GetMapping("/yoy-growth")
    public ResponseEntity<YoYGrowthDto> yoyGrowth(
            @RequestParam int year
    ) {
        return ResponseEntity.ok(dashboardService.yoyGrowth(year));
    }

    // MULTI-YEAR COMPARISON
    @Operation(summary = "Multi-year income vs expense comparison")
    @GetMapping("/yearly-comparison")
    public ResponseEntity<List<YearlyComparisonDto>> yearlyComparison() {
        return ResponseEntity.ok(dashboardService.yearlyComparison());
    }
}
