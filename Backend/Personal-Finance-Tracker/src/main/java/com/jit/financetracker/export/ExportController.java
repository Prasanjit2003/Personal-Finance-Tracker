package com.jit.financetracker.export;

import com.jit.financetracker.dashboard.service.DashboardService;
import com.jit.financetracker.service.ExpenseService;
import com.jit.financetracker.service.IncomeService;
import com.jit.financetracker.service.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    private final ExportService exportService;
    private final TransactionService transactionService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final DashboardService dashboardService;

    public ExportController(
            ExportService exportService,
            TransactionService transactionService,
            ExpenseService expenseService,
            IncomeService incomeService,
            DashboardService dashboardService
    ) {
        this.exportService = exportService;
        this.transactionService = transactionService;
        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.dashboardService = dashboardService;
    }

    // TRANSACTIONS 
    @GetMapping("/transactions/csv")
    public ResponseEntity<byte[]> exportTransactionsCsv() {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"ID", "Type", "Amount", "Category", "Date"});
        rows.addAll(exportService.transactionsToRows(
                transactionService.getAllTransactions()
        ));

        return buildCsv("transactions.csv", rows);
    }

    @GetMapping("/transactions/excel")
    public ResponseEntity<byte[]> exportTransactionsExcel() {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"ID", "Type", "Amount", "Category", "Date"});
        rows.addAll(exportService.transactionsToRows(
                transactionService.getAllTransactions()
        ));

        return buildExcel("transactions.xlsx", "Transactions", rows);
    }

    // EXPENSE
    @GetMapping("/expenses/csv")
    public ResponseEntity<byte[]> exportExpensesCsv() {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"ID", "Category", "Amount", "Description", "Date"});
        rows.addAll(exportService.expensesToRows(
                expenseService.getMyExpenses()
        ));

        return buildCsv("expenses.csv", rows);
    }

    @GetMapping("/expenses/excel")
    public ResponseEntity<byte[]> exportExpensesExcel() {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"ID", "Category", "Amount", "Description", "Date"});
        rows.addAll(exportService.expensesToRows(
                expenseService.getMyExpenses()
        ));

        return buildExcel("expenses.xlsx", "Expenses", rows);
    }

    // INCOME
    @GetMapping("/income/csv")
    public ResponseEntity<byte[]> exportIncomeCsv() {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"ID", "Source", "Amount", "Frequency", "Date"});
        rows.addAll(exportService.incomeToRows(
                incomeService.getMyIncome()
        ));

        return buildCsv("income.csv", rows);
    }

    @GetMapping("/income/excel")
    public ResponseEntity<byte[]> exportIncomeExcel() {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"ID", "Source", "Amount", "Frequency", "Date"});
        rows.addAll(exportService.incomeToRows(
                incomeService.getMyIncome()
        ));

        return buildExcel("income.xlsx", "Income", rows);
    }

    // YEARLY COMPARISON
    @GetMapping("/yearly-comparison/csv")
    public ResponseEntity<byte[]> exportYearlyComparisonCsv() {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Year", "Income", "Expense"});
        rows.addAll(exportService.yearlyComparisonToRows(
                dashboardService.yearlyComparison()
        ));

        return buildCsv("yearly-comparison.csv", rows);
    }

    @GetMapping("/yearly-comparison/excel")
    public ResponseEntity<byte[]> exportYearlyComparisonExcel() {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Year", "Income", "Expense"});
        rows.addAll(exportService.yearlyComparisonToRows(
                dashboardService.yearlyComparison()
        ));

        return buildExcel("yearly-comparison.xlsx", "Yearly Comparison", rows);
    }

    // RESPONSE BUILDERS
    private ResponseEntity<byte[]> buildCsv(String filename, List<String[]> rows) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.TEXT_PLAIN)
                .body(exportService.toCsv(rows));
    }

    private ResponseEntity<byte[]> buildExcel(
            String filename,
            String sheetName,
            List<String[]> rows
    ) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(exportService.toExcel(sheetName, rows));
    }
}
