package com.jit.financetracker.export;

import com.jit.financetracker.dashboard.dto.MonthlyComparisonDto;
import com.jit.financetracker.dashboard.dto.YearlyComparisonDto;
import com.jit.financetracker.dto.response.ExpenseResponseDto;
import com.jit.financetracker.dto.response.IncomeResponseDto;
import com.jit.financetracker.dto.response.TransactionResponseDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExportService {

    // CSV 
    public byte[] toCsv(List<String[]> rows) {

        StringBuilder sb = new StringBuilder();

        for (String[] row : rows) {
            sb.append(String.join(",", row)).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    // EXCEL 
    public byte[] toExcel(String sheetName, List<String[]> rows) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetName);

            for (int i = 0; i < rows.size(); i++) {
                Row row = sheet.createRow(i);
                String[] data = rows.get(i);

                for (int j = 0; j < data.length; j++) {
                    row.createCell(j).setCellValue(data[j]);
                }
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel file");
        }
    }

    // HELPERS 
    public List<String[]> transactionsToRows(List<TransactionResponseDto> list) {
        return list.stream()
                .map(t -> new String[]{
                        t.getId().toString(),
                        t.getType().name(),
                        t.getAmount().toString(),
                        t.getCategory(),
                        t.getTransactionDate().toString()
                })
                .toList();
    }

    public List<String[]> expensesToRows(List<ExpenseResponseDto> list) {
        return list.stream()
                .map(e -> new String[]{
                        e.getId().toString(),
                        e.getCategory().name(),
                        e.getAmount().toString(),
                        e.getDescription(),
                        e.getExpenseDate().toString()
                })
                .toList();
    }

    public List<String[]> incomeToRows(List<IncomeResponseDto> list) {
        return list.stream()
                .map(i -> new String[]{
                        i.getId().toString(),
                        i.getSource(),
                        i.getAmount().toString(),
                        i.getFrequency().name(),
                        i.getIncomeDate().toString()
                })
                .toList();
    }

    public List<String[]> yearlyComparisonToRows(List<YearlyComparisonDto> list) {
        return list.stream()
                .map(y -> new String[]{
                        String.valueOf(y.getYear()),
                        y.getIncome().toString(),
                        y.getExpense().toString()
                })
                .toList();
    }

    public List<String[]> monthlyComparisonToRows(List<MonthlyComparisonDto> list) {
        return list.stream()
                .map(m -> new String[]{
                        m.getMonth(),
                        m.getIncome().toString(),
                        m.getExpense().toString()
                })
                .toList();
    }
}
