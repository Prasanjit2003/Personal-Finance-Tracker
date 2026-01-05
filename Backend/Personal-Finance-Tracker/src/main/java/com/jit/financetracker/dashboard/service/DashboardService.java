package com.jit.financetracker.dashboard.service;

import com.jit.financetracker.dashboard.dto.CategoryExpenseDto;
import com.jit.financetracker.dashboard.dto.DashboardSummaryDto;
import com.jit.financetracker.dashboard.dto.MonthlyComparisonDto;
import com.jit.financetracker.dashboard.dto.YearlyComparisonDto;
import com.jit.financetracker.dashboard.dto.YearlyMonthlyDto;
import com.jit.financetracker.dashboard.dto.YearlySummaryDto;
import com.jit.financetracker.dashboard.dto.YoYGrowthDto;
import com.jit.financetracker.dashboard.projection.CategoryExpenseProjection;
import com.jit.financetracker.dashboard.projection.MonthlyComparisonProjection;
import com.jit.financetracker.dashboard.projection.YearlyComparisonProjection;
import com.jit.financetracker.dashboard.projection.YearlyMonthlyProjection;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.TransactionType;
import com.jit.financetracker.exception.ResourceNotFoundException;
import com.jit.financetracker.repository.TransactionRepository;
import com.jit.financetracker.repository.UserRepository;
import com.jit.financetracker.util.DateUtil;
import com.jit.financetracker.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class DashboardService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public DashboardService(
            TransactionRepository transactionRepository,
            UserRepository userRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // SUMMARY 
    public DashboardSummaryDto getSummary() {

        User user = getCurrentUser();

        BigDecimal totalIncome =
                transactionRepository.sumByUserAndType(user, TransactionType.INCOME);

        BigDecimal totalExpense =
                transactionRepository.sumByUserAndType(user, TransactionType.EXPENSE);

        YearMonth now = YearMonth.now();
        LocalDateTime start = DateUtil.startOfMonth(now);
        LocalDateTime end   = DateUtil.endOfMonth(now);

        BigDecimal currentMonthExpense =
                transactionRepository.sumByUserAndTypeAndDateRange(
                        user,
                        TransactionType.EXPENSE,
                        start,
                        end
                );

        return new DashboardSummaryDto(
                totalIncome,
                totalExpense,
                totalIncome.subtract(totalExpense),
                currentMonthExpense
        );
    }

    // CATEGORY PIE
    public List<CategoryExpenseDto> expenseByCategory() {

        return transactionRepository
                .expenseByCategory(getCurrentUser())
                .stream()
                .map(p -> new CategoryExpenseDto(
                        p.getCategory(),
                        p.getAmount()
                ))
                .toList();
    }

    // MONTHLY COMPARISON 
    public List<MonthlyComparisonDto> monthlyComparison() {

        User user = getCurrentUser();

        return transactionRepository
                .monthlyComparison(user)
                .stream()
                .map(p -> new MonthlyComparisonDto(
                        p.getMonth(),
                        p.getIncome(),
                        p.getExpense()
                ))
                .toList();
    }

    // YEARLY SUMMARY 
    public YearlySummaryDto yearlySummary(int year) {

        User user = getCurrentUser();

        BigDecimal income =
                transactionRepository.sumByUserAndTypeAndYear(
                        user, TransactionType.INCOME, year
                );

        BigDecimal expense =
                transactionRepository.sumByUserAndTypeAndYear(
                        user, TransactionType.EXPENSE, year
                );

        return new YearlySummaryDto(
                year,
                income,
                expense,
                income.subtract(expense)
        );
    }

    // YEARLY MONTHLY
    public List<YearlyMonthlyDto> yearlyMonthlyBreakdown(int year) {

        User user = getCurrentUser();

        return transactionRepository
                .yearlyMonthlyBreakdown(user, year)
                .stream()
                .map(p -> new YearlyMonthlyDto(
                        p.getMonth(),
                        p.getIncome(),
                        p.getExpense()
                ))
                .toList();
    }

    // YOY GROWTH
    public YoYGrowthDto yoyGrowth(int year) {

        User user = getCurrentUser();

        BigDecimal incomeThisYear =
                transactionRepository.sumByUserAndTypeAndYear(
                        user, TransactionType.INCOME, year
                );

        BigDecimal incomeLastYear =
                transactionRepository.sumByUserAndTypeAndYear(
                        user, TransactionType.INCOME, year - 1
                );

        BigDecimal expenseThisYear =
                transactionRepository.sumByUserAndTypeAndYear(
                        user, TransactionType.EXPENSE, year
                );

        BigDecimal expenseLastYear =
                transactionRepository.sumByUserAndTypeAndYear(
                        user, TransactionType.EXPENSE, year - 1
                );

        BigDecimal balanceThisYear = incomeThisYear.subtract(expenseThisYear);
        BigDecimal balanceLastYear = incomeLastYear.subtract(expenseLastYear);

        return new YoYGrowthDto(
                year,
                calculateGrowth(incomeThisYear, incomeLastYear),
                calculateGrowth(expenseThisYear, expenseLastYear),
                calculateGrowth(balanceThisYear, balanceLastYear)
        );
    }

    // YEARLY COMPARISON 
    public List<YearlyComparisonDto> yearlyComparison() {

        return transactionRepository
                .yearlyComparison(getCurrentUser())
                .stream()
                .map(p -> new YearlyComparisonDto(
                        p.getYear(),
                        p.getIncome(),
                        p.getExpense()
                ))
                .toList();
    }

    // HELPERS
    private BigDecimal calculateGrowth(BigDecimal current, BigDecimal previous) {

        if (previous.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return current
                .subtract(previous)
                .divide(previous, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
