package com.jit.financetracker.forecast.service;

import com.jit.financetracker.entity.User;
import com.jit.financetracker.enums.TransactionType;
import com.jit.financetracker.exception.ResourceNotFoundException;
import com.jit.financetracker.forecast.dto.ForecastPointDto;
import com.jit.financetracker.forecast.dto.TrendSummaryDto;
import com.jit.financetracker.repository.TransactionRepository;
import com.jit.financetracker.repository.UserRepository;
import com.jit.financetracker.util.ForecastUtil;
import com.jit.financetracker.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public ForecastService(
            TransactionRepository transactionRepository,
            UserRepository userRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // FORECAST 
    public List<ForecastPointDto> forecast(
            TransactionType type,
            int months
    ) {

        List<Object[]> raw = transactionRepository
                .monthlyTotalsByType(getCurrentUser(), type);

        if (raw.size() < 3) {
            return List.of();
        }

        List<BigDecimal> values = raw.stream()
                .map(r -> (BigDecimal) r[1])
                .toList();

        // ✅ CLEANED USING UTIL
        BigDecimal avg = ForecastUtil.average(values);

        List<ForecastPointDto> forecast = new ArrayList<>();
        YearMonth start = YearMonth.now().plusMonths(1);

        for (int i = 0; i < months; i++) {
            forecast.add(new ForecastPointDto(
                    start.plusMonths(i).toString(),
                    avg
            ));
        }

        return forecast;
    }

    // TREND 
    public TrendSummaryDto trend(TransactionType type) {

        List<Object[]> raw = transactionRepository
                .monthlyTotalsByType(getCurrentUser(), type);

        if (raw.size() < 2) {
            return new TrendSummaryDto(type.name(), "STABLE", 0);
        }

        BigDecimal first = (BigDecimal) raw.get(0)[1];
        BigDecimal last  = (BigDecimal) raw.get(raw.size() - 1)[1];
       
        BigDecimal slope = ForecastUtil.slope(first, last, raw.size());

        String trend;
        if (slope.compareTo(BigDecimal.ZERO) > 0) {
            trend = "UP";
        } else if (slope.compareTo(BigDecimal.ZERO) < 0) {
            trend = "DOWN";
        } else {
            trend = "STABLE";
        }

        return new TrendSummaryDto(
                type.name(),
                trend,
                slope.doubleValue()
        );
    }

    // HELPER 
    private User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
