package com.jit.financetracker.service;

import com.jit.financetracker.dto.request.IncomeRequestDto;
import com.jit.financetracker.dto.response.IncomeResponseDto;
import com.jit.financetracker.entity.Income;
import com.jit.financetracker.entity.Transaction;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.TransactionType;
import com.jit.financetracker.exception.BadRequestException;
import com.jit.financetracker.exception.ResourceNotFoundException;
import com.jit.financetracker.repository.IncomeRepository;
import com.jit.financetracker.repository.TransactionRepository;
import com.jit.financetracker.repository.UserRepository;
import com.jit.financetracker.util.PaginationUtil;
import com.jit.financetracker.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public IncomeService(
            IncomeRepository incomeRepository,
            UserRepository userRepository,
            TransactionRepository transactionRepository
    ) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ADD INCOME
    public IncomeResponseDto addIncome(IncomeRequestDto request) {

        User user = getCurrentUser();

        Income income = Income.builder()
                .amount(request.getAmount())
                .source(request.getSource())
                .frequency(request.getFrequency())
                .incomeDate(request.getIncomeDate())
                .user(user)
                .build();

        Income saved = incomeRepository.save(income);

        transactionRepository.save(Transaction.builder()
                .type(TransactionType.INCOME)
                .amount(request.getAmount())
                .category(request.getSource())
                .transactionDate(LocalDateTime.now())
                .user(user)
                .build());

        return map(saved);
    }

    // GET ALL 
    public List<IncomeResponseDto> getMyIncome() {

        Pageable unpaged = Pageable.unpaged();

        return incomeRepository
                .findByUser(getCurrentUser(), unpaged)
                .stream()
                .map(this::map)
                .toList();
    }

    // PAGINATED
    public Page<IncomeResponseDto> getIncomesPaged(
            int page,
            int size,
            String sortBy,
            String direction,
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        PageRequest pageable =
                PaginationUtil.create(page, size, sortBy, direction);

        User user = getCurrentUser();

        return (startDate != null && endDate != null
                ? incomeRepository.findByUserAndIncomeDateBetween(
                        user, startDate, endDate, pageable
                )
                : incomeRepository.findByUser(user, pageable)
        ).map(this::map);
    }

    //DELETE 
    public void deleteIncome(Long id) {

        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found"));

        if (!income.getUser().getId().equals(getCurrentUser().getId())) {
            throw new ResourceNotFoundException("Income not found");
        }

        incomeRepository.delete(income);
    }

    // HELPERS 
    private User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private IncomeResponseDto map(Income income) {
        return new IncomeResponseDto(
                income.getId(),
                income.getAmount(),
                income.getSource(),
                income.getFrequency(),
                income.getIncomeDate()
        );
    }
}
