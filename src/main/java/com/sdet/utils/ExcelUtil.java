package com.sdet.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static List<Map<String, String>> readExcelData(String filePath, String sheetName){
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)){

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null){
                throw new RuntimeException("Sheet '" + sheetName + "' not found in: " + filePath);
            }
            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String firstCellValue = getCellValue(row.getCell(0));
                if (firstCellValue.isEmpty()) continue;

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < columnCount; j++){
                    String header = getCellValue(headerRow.getCell(j));
                    String value = getCellValue(row.getCell(j));
                    rowData.put(header, value);
                }
                dataList.add(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }

        return dataList;
    }

    private static String getCellValue(Cell cell){
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
