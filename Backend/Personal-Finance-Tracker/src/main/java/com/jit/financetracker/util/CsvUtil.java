package com.jit.financetracker.util;

import java.nio.charset.StandardCharsets;
import java.util.List;

public final class CsvUtil {

    private CsvUtil() {}

    public static byte[] write(List<String[]> rows) {

        StringBuilder sb = new StringBuilder();

        for (String[] row : rows) {
            sb.append(String.join(",", row)).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
