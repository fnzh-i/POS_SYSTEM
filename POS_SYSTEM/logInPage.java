package POS_SYSTEM;

import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import java.io.*;
import java.util.*;


public class logInPage extends logInSection{
    private static final String FILE_PATH = "db/systemAccounts.xlsx"; // DATABASE FOR ACCOUNTS

    public static List<List<Object>> readExcel(String filePath) {
        List<List<Object>> data = new ArrayList<>();

        try (InputStream inputStream = logInPage.class.getClassLoader().getResourceAsStream(filePath);
             XSSFWorkbook workbook = inputStream != null ? new XSSFWorkbook(inputStream)
                     : new XSSFWorkbook(new FileInputStream(filePath))) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();

            if (rows < 0) {
                System.out.println("The sheet is empty");
                return data;
            }

            int cols = sheet.getRow(0).getLastCellNum();

            for (int r = 0; r <= rows; r++) {
                XSSFRow row = sheet.getRow(r);
                List<Object> rowData = new ArrayList<>();

                if (row != null) {
                    for (int c = 0; c < cols; c++) {
                        XSSFCell cell = row.getCell(c, XSSFRow.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        rowData.add(getCellValue(cell));
                    }
                }
                data.add(rowData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Excel file not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
        }
        return data;
    }
    private static Object getCellValue(XSSFCell cell) {
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                double numValue = cell.getNumericCellValue();
                return numValue == (int)numValue ? (int)numValue : numValue;
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    public static boolean authenticateUser(String userName, String password) {
        List<List<Object>> data = readExcel(FILE_PATH);
        for (List<Object> row : data) {
            // Skip null rows or rows with insufficient data
            if (row == null || row.size() < 5) continue;

            Object usernameObj = row.get(3);
            Object passwordObj = row.get(4);

            if (usernameObj == null || passwordObj == null) continue;

            if (userName.equals(usernameObj.toString()) &&
                    password.equals(passwordObj.toString())) {
                return true;
            }
        }

        JOptionPane.showMessageDialog(null,
                "User or password was not found in database",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
