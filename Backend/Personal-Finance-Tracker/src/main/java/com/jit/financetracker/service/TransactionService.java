package com.jit.financetracker.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jit.financetracker.dto.response.TransactionResponseDto;
import com.jit.financetracker.entity.Transaction;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.TransactionType;
import com.jit.financetracker.exception.BadRequestException;
import com.jit.financetracker.repository.TransactionRepository;
import com.jit.financetracker.repository.UserRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserRepository userRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // LEGACY
    public List<TransactionResponseDto> getAllTransactions() {

        Pageable unpaged = Pageable.unpaged();

        return transactionRepository
                .findByUser(getCurrentUser(), unpaged)
                .map(this::mapToResponse)
                .toList();
    }

    // GET ALL TRANSACTIONS (PAGED)
    public Page<TransactionResponseDto> getAllTransactionsPaged(
            int page,
            int size
    ) {
        User user = getCurrentUser();

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by("transactionDate").descending()
        );

        return transactionRepository
                .findByUser(user, pageable)
                .map(this::mapToResponse);
    }

    // FILTER BY DATE
    public Page<TransactionResponseDto> getTransactionsByDateRange(
            int page,
            int size,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        User user = getCurrentUser();

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by("transactionDate").descending()
        );

        return transactionRepository
                .findByUserAndTransactionDateBetween(user, start, end, pageable)
                .map(this::mapToResponse);
    }

    // FILTER + PAGINATION
    public Page<TransactionResponseDto> getTransactions(
            int page,
            int size,
            String sortBy,
            String direction,
            TransactionType type,
            LocalDate startDate,
            LocalDate endDate
    ) {

        User user = getCurrentUser();

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<Transaction> result;

        if (type != null) {
            result = transactionRepository.findByUserAndType(user, type, pageable);
        } else if (startDate != null && endDate != null) {

            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(23, 59, 59);

            result = transactionRepository
                    .findByUserAndTransactionDateBetween(user, start, end, pageable);
        } else {
            result = transactionRepository.findByUser(user, pageable);
        }

        return result.map(this::mapToResponse);
    }

    // HELPERS
    private TransactionResponseDto mapToResponse(Transaction transaction) {

        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getTransactionDate()
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
