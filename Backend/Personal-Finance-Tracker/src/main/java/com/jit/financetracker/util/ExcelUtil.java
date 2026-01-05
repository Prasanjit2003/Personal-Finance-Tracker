package com.jit.financetracker.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

public final class ExcelUtil {

    private ExcelUtil() {}

    public static byte[] write(String sheetName, List<String[]> rows) {

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
}
