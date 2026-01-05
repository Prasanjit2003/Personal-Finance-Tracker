package com.jit.financetracker.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class ForecastUtil {

    private ForecastUtil() {}

    public static BigDecimal average(List<BigDecimal> values) {

        if (values.isEmpty()) return BigDecimal.ZERO;

        return values.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(
                        BigDecimal.valueOf(values.size()),
                        2,
                        RoundingMode.HALF_UP
                );
    }

    public static BigDecimal slope(
            BigDecimal first,
            BigDecimal last,
            int points
    ) {
        if (points <= 1) return BigDecimal.ZERO;

        return last.subtract(first)
                .divide(BigDecimal.valueOf(points), 2, RoundingMode.HALF_UP);
    }
}
