package com.jit.financetracker.service;

import com.jit.financetracker.dto.request.ExpenseRequestDto;
import com.jit.financetracker.dto.response.ExpenseResponseDto;
import com.jit.financetracker.entity.Expense;
import com.jit.financetracker.entity.Transaction;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.ExpenseCategory;
import com.jit.financetracker.enums.TransactionType;
import com.jit.financetracker.exception.BadRequestException;
import com.jit.financetracker.exception.ResourceNotFoundException;
import com.jit.financetracker.repository.ExpenseRepository;
import com.jit.financetracker.repository.TransactionRepository;
import com.jit.financetracker.repository.UserRepository;
import com.jit.financetracker.util.PaginationUtil;
import com.jit.financetracker.util.SecurityUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            UserRepository userRepository,
            TransactionRepository transactionRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ADD 
    public ExpenseResponseDto addExpense(ExpenseRequestDto request) {

        User user = getCurrentUser();

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .category(request.getCategory())
                .description(request.getDescription())
                .expenseDate(request.getExpenseDate())
                .user(user)
                .build();

        Expense saved = expenseRepository.save(expense);

        transactionRepository.save(Transaction.builder()
                .type(TransactionType.EXPENSE)
                .amount(request.getAmount())
                .category(request.getCategory().name())
                .transactionDate(LocalDateTime.now())
                .user(user)
                .build());

        return map(saved);
    }

    //  GET ALL
    public List<ExpenseResponseDto> getMyExpenses() {

        Pageable unpaged = Pageable.unpaged();

        return expenseRepository
                .findByUser(getCurrentUser(), unpaged)
                .stream()
                .map(this::map)
                .toList();
    }

    //  PAGED
    public org.springframework.data.domain.Page<ExpenseResponseDto> getExpensesPaged(
            int page,
            int size,
            String sortBy,
            String direction,
            ExpenseCategory category,
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        PageRequest pageable =
                PaginationUtil.create(page, size, sortBy, direction);

        User user = getCurrentUser();

        return (category != null
                ? expenseRepository.findByUserAndCategory(user, category, pageable)
                : startDate != null && endDate != null
                ? expenseRepository.findByUserAndExpenseDateBetween(user, startDate, endDate, pageable)
                : expenseRepository.findByUser(user, pageable)
        ).map(this::map);
    }

    // DELETE
    public void deleteExpense(Long id) {

        Expense e = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        if (!e.getUser().getId().equals(getCurrentUser().getId())) {
            throw new ResourceNotFoundException("Expense not found");
        }

        expenseRepository.delete(e);
    }

    // HELPERS 
    private User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ExpenseResponseDto map(Expense e) {
        return new ExpenseResponseDto(
                e.getId(),
                e.getAmount(),
                e.getCategory(),
                e.getDescription(),
                e.getExpenseDate()
        );
    }
}
