package com.jit.financetracker.repository;

import com.jit.financetracker.entity.Expense;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByUser(User user, Pageable pageable);

    Page<Expense> findByUserAndCategory(
            User user,
            ExpenseCategory category,
            Pageable pageable
    );

    Page<Expense> findByUserAndExpenseDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}
