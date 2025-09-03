package serenity.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class excelDataReader {

    public static List<Map<String, String>> readDataFromExcel(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            throw new IllegalArgumentException("Hoja de cálculo no encontrada: " + sheetName);
        }

        Row headerRow = sheet.getRow(sheet.getFirstRowNum());
        List<String> columnNames = new ArrayList<>();
        if (headerRow != null) {
            for (Cell cell : headerRow) {
                columnNames.add(cell.toString().trim());
            }
        }

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            Row dataRow = sheet.getRow(i);
            if (dataRow != null) {
                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < columnNames.size(); j++) {
                    Cell cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue;


                    if (cell.getCellType() == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellValue = cell.getDateCellValue().toString();
                        } else {
                            double numericValue = cell.getNumericCellValue();
                            if (numericValue == (long) numericValue) {
                                cellValue = String.valueOf((long) numericValue);
                            } else {
                                cellValue = String.valueOf(numericValue);
                            }
                        }
                    } else {
                        cellValue = cell.toString().trim();
                    }

                    String columnName = columnNames.get(j);
                    rowData.put(columnName, cellValue);
                }
                data.add(rowData);
            }
        }

        workbook.close();
        file.close();
        return data;
    }

    public static void updateExcelCell(String filePath, String sheetName, int rowNumber, String columnName, String newValue) throws IOException {
        FileInputStream fileIn = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileIn);
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            workbook.close();
            fileIn.close();
            throw new IllegalArgumentException("Hoja de cálculo no encontrada: " + sheetName);
        }

        // Obtener el índice de la columna por su nombre
        Row headerRow = sheet.getRow(0);
        int columnIndex = -1;
        if (headerRow != null) {
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                    columnIndex = cell.getColumnIndex();
                    break;
                }
            }
        }

        if (columnIndex == -1) {
            workbook.close();
            fileIn.close();
            throw new IllegalArgumentException("Columna no encontrada: " + columnName);
        }

        // Obtener la fila a actualizar
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            row = sheet.createRow(rowNumber); // Si la fila no existe, se crea
        }

        // Obtener o crear la celda y establecer el nuevo valor
        Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(newValue);

        // Escribir los cambios en el archivo
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
            fileIn.close();
        }
    }
}
