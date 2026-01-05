package com.jit.financetracker.service;

import com.jit.financetracker.dto.request.BudgetRequestDto;
import com.jit.financetracker.dto.response.BudgetResponseDto;
import com.jit.financetracker.entity.Budget;
import com.jit.financetracker.entity.Expense;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.ExpenseCategory;
import com.jit.financetracker.exception.BadRequestException;
import com.jit.financetracker.repository.BudgetRepository;
import com.jit.financetracker.repository.ExpenseRepository;
import com.jit.financetracker.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public BudgetService(
            BudgetRepository budgetRepository,
            ExpenseRepository expenseRepository,
            UserRepository userRepository
    ) {
        this.budgetRepository = budgetRepository;
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    // CREATE / UPDATE BUDGET
    public BudgetResponseDto saveBudget(BudgetRequestDto request) {

        User user = getCurrentUser();

        // 🔥 FIX: parse only from request, store as String
        YearMonth yearMonth = YearMonth.parse(request.getMonth());
        ExpenseCategory category = request.getCategory();
        String month = yearMonth.toString();

        Budget budget = budgetRepository
                .findByUserAndCategoryAndMonth(user, category, month)
                .orElse(
                        Budget.builder()
                                .user(user)
                                .category(category)
                                .month(month)
                                .build()
                );

        budget.setMonthlyLimit(request.getMonthlyLimit());

        Budget savedBudget = budgetRepository.save(budget);

        return buildBudgetResponse(savedBudget, user);
    }

    // GET ALL BUDGETS
    public List<BudgetResponseDto> getMyBudgets() {

        User user = getCurrentUser();

        return budgetRepository.findAll()
                .stream()
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .map(b -> buildBudgetResponse(b, user))
                .toList();
    }

    // DELETE BUDGET 
    public void deleteBudget(Long budgetId) {

        User user = getCurrentUser();

        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BadRequestException("Budget not found"));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Budget not found");
        }

        budgetRepository.delete(budget);
    }

    // HELPER METHODS 
    private BudgetResponseDto buildBudgetResponse(Budget budget, User user) {

        // convert String -> YearMonth safely
        YearMonth ym = YearMonth.parse(budget.getMonth());
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        List<Expense> expenses = expenseRepository
                .findByUserAndExpenseDateBetween(
                        user,
                        start,
                        end,
                        Pageable.unpaged()
                )
                .stream()
                .filter(e -> e.getCategory() == budget.getCategory())
                .toList();

        BigDecimal spent = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remaining = budget.getMonthlyLimit().subtract(spent);

        return new BudgetResponseDto(
                budget.getId(),
                budget.getCategory(),
                budget.getMonth(),
                budget.getMonthlyLimit(),
                spent,
                remaining
        );
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));
    }
}
