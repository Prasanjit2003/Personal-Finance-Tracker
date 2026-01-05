package com.jit.financetracker.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jit.financetracker.dashboard.projection.CategoryExpenseProjection;
import com.jit.financetracker.dashboard.projection.MonthlyComparisonProjection;
import com.jit.financetracker.dashboard.projection.YearlyComparisonProjection;
import com.jit.financetracker.dashboard.projection.YearlyMonthlyProjection;
import com.jit.financetracker.entity.Transaction;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByUser(
            User user,
            Pageable pageable
    );

    Page<Transaction> findByUserAndType(
            User user,
            TransactionType type,
            Pageable pageable
    );

    Page<Transaction> findByUserAndTransactionDateBetween(
            User user,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.user = :user AND t.type = :type
    """)
    BigDecimal sumByUserAndType(
            @Param("user") User user,
            @Param("type") TransactionType type
    );

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.user = :user
          AND t.type = :type
          AND t.transactionDate BETWEEN :start AND :end
    """)
    BigDecimal sumByUserAndTypeAndDateRange(
            @Param("user") User user,
            @Param("type") TransactionType type,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // ✅ FIXED: projection instead of DTO
    @Query("""
        SELECT
            t.category AS category,
            SUM(t.amount) AS amount
        FROM Transaction t
        WHERE t.user = :user AND t.type = 'EXPENSE'
        GROUP BY t.category
    """)
    List<CategoryExpenseProjection> expenseByCategory(@Param("user") User user);

    @Query("""
        SELECT
            FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m') AS month,
            SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) AS income,
            SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) AS expense
        FROM Transaction t
        WHERE t.user = :user
        GROUP BY FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m')
        ORDER BY FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m')
    """)
    List<MonthlyComparisonProjection> monthlyComparison(User user);

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.user = :user
          AND t.type = :type
          AND FUNCTION('YEAR', t.transactionDate) = :year
    """)
    BigDecimal sumByUserAndTypeAndYear(
            @Param("user") User user,
            @Param("type") TransactionType type,
            @Param("year") int year
    );

    @Query("""
        SELECT
            FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m') AS month,
            SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) AS income,
            SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) AS expense
        FROM Transaction t
        WHERE t.user = :user
          AND FUNCTION('YEAR', t.transactionDate) = :year
        GROUP BY FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m')
        ORDER BY FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m')
    """)
    List<YearlyMonthlyProjection> yearlyMonthlyBreakdown(
            @Param("user") User user,
            @Param("year") int year
    );

    @Query("""
        SELECT
            FUNCTION('YEAR', t.transactionDate) AS year,
            SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) AS income,
            SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) AS expense
        FROM Transaction t
        WHERE t.user = :user
        GROUP BY FUNCTION('YEAR', t.transactionDate)
        ORDER BY FUNCTION('YEAR', t.transactionDate)
    """)
    List<YearlyComparisonProjection> yearlyComparison(@Param("user") User user);

    @Query("""
        SELECT FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m'),
               SUM(t.amount)
        FROM Transaction t
        WHERE t.user = :user
          AND t.type = :type
        GROUP BY FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m')
        ORDER BY FUNCTION('DATE_FORMAT', t.transactionDate, '%Y-%m')
    """)
    List<Object[]> monthlyTotalsByType(
            @Param("user") User user,
            @Param("type") TransactionType type
    );
}
