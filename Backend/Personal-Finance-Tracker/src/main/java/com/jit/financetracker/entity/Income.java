package com.jit.financetracker.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jit.financetracker.enums.IncomeFrequency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "incomes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Income extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String source;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncomeFrequency frequency;

    @Column(nullable = false)
    private LocalDate incomeDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
