package com.jit.financetracker.repository;

import com.jit.financetracker.entity.Income;
import com.jit.financetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    Page<Income> findByUser(User user, Pageable pageable);

    Page<Income> findByUserAndIncomeDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}
