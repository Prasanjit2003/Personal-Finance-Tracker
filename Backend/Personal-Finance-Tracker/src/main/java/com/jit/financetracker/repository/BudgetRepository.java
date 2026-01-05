package com.jit.financetracker.repository;

import com.jit.financetracker.entity.Budget;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserAndCategoryAndMonth(
            User user,
            ExpenseCategory category,
            String month
    );
}
