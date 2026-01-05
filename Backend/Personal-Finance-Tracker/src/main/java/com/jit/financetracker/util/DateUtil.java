package com.jit.financetracker.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

public final class DateUtil {

    private DateUtil() {}

    public static LocalDateTime startOfMonth(YearMonth ym) {
        return ym.atDay(1).atStartOfDay();
    }

    public static LocalDateTime endOfMonth(YearMonth ym) {
        return ym.atEndOfMonth().atTime(23, 59, 59);
    }

    public static YearMonth parseYearMonth(String value) {
        return YearMonth.parse(value);
    }

    public static boolean isValidRange(LocalDate start, LocalDate end) {
        return start == null || end == null || !start.isAfter(end);
    }
}
