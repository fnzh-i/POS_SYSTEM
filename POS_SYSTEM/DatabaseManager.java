import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

public class DatabaseManager {
    private static final String EXCEL_FILE_PATH = "C:\\Users\\johnc\\IdeaProjects\\Project\\ProductItem.xlsx";

    public static void addProductToDatabase(String date, int itemId, String productName, String productSize, double price, int quantity) {
        try {
            // Create a new workbook if the file does not exist
            File file = new File(EXCEL_FILE_PATH);
            Workbook workbook;
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileInputStream);
            } else {
                workbook = new XSSFWorkbook();
            }

            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                sheet = workbook.createSheet();
            }

            // Get the last row
            int lastRow = sheet.getLastRowNum();
            if (lastRow == -1) {
                lastRow = 0;
            }

            // Create a new row
            Row row = sheet.createRow(lastRow + 1);



            // Set the values
            Cell cell = row.createCell(0);
            cell.setCellValue(date);




            cell = row.createCell(1);
            cell.setCellValue(itemId);

            cell = row.createCell(2);
            cell.setCellValue(productName);

            cell = row.createCell(3);
            cell.setCellValue(productSize);


            cell = row.createCell(4);
            cell.setCellValue(price);

            cell = row.createCell(5);
            cell.setCellValue(quantity);





            // Write the data to the file
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            // Close the workbook
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<Integer, OrderData> getOrderHistory() {
        Map<Integer, OrderData> orderMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) return orderMap;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell dateCell = row.getCell(0);
                Cell orderIdCell = row.getCell(1);
                Cell productNameCell = row.getCell(2);
                Cell priceCell = row.getCell(4);
                Cell quantityCell = row.getCell(5);

                if (dateCell == null || orderIdCell == null || productNameCell == null ||
                        priceCell == null || quantityCell == null) {
                    continue; // skip incomplete row
                }

                // Check cell types before accessing
                if (dateCell.getCellType() != CellType.STRING ||
                        orderIdCell.getCellType() != CellType.NUMERIC ||
                        productNameCell.getCellType() != CellType.STRING ||
                        priceCell.getCellType() != CellType.NUMERIC ||
                        quantityCell.getCellType() != CellType.NUMERIC) {
                    continue; // skip row with unexpected types
                }

                String date = dateCell.getStringCellValue();
                int orderId = (int) orderIdCell.getNumericCellValue();
                String productName = productNameCell.getStringCellValue();
                double price = priceCell.getNumericCellValue();
                int quantity = (int) quantityCell.getNumericCellValue();

                double total = price * quantity;
                double vat = 0.12;
                double tax = total * vat;
                double totalAmount = tax + total;

                orderMap.putIfAbsent(orderId, new OrderData(orderId, date));
                orderMap.get(orderId).addProduct(productName, totalAmount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderMap;
    }
    public static double getTodaysIncome() {
        double todaysIncome = 0.0;
        double totalAmount = 0.0;

        // Format for only date (e.g., "Sunday, May 18, 2025")
        SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
        String todayDateOnly = dateOnlyFormat.format(new Date());

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) return 0.0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell dateCell = row.getCell(0);
                Cell priceCell = row.getCell(4);
                Cell quantityCell = row.getCell(5);

                if (dateCell == null || priceCell == null || quantityCell == null) continue;

                String cellDateOnly = null;

                if (dateCell.getCellType() == CellType.STRING) {
                    try {
                        Date parsedDate = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a", Locale.ENGLISH)
                                .parse(dateCell.getStringCellValue());
                        cellDateOnly = dateOnlyFormat.format(parsedDate);
                    } catch (Exception e) {
                        continue;
                    }
                } else if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                    Date parsedDate = dateCell.getDateCellValue();
                    cellDateOnly = dateOnlyFormat.format(parsedDate);
                } else {
                    continue;
                }

                // Compare only date parts
                if (cellDateOnly != null && cellDateOnly.equals(todayDateOnly)) {
                    double price = priceCell.getNumericCellValue();
                    int quantity = (int) quantityCell.getNumericCellValue();
                    double vat = 0.12;
                    double tax;
                    todaysIncome += price * quantity; // ✅ FIXED: Full income, not just VAT
                    tax =  todaysIncome * vat;
                    totalAmount = todaysIncome  + tax;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalAmount;
    }



    public static double getTotalIncome() {
        double total = 0;
        double totalAmount = 0.0;

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) return 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell priceCell = row.getCell(4);
                Cell quantityCell = row.getCell(5);

                if (priceCell != null && quantityCell != null) {
                    double price = priceCell.getNumericCellValue();
                    int quantity = (int) quantityCell.getNumericCellValue();
                    double tax;
                    total += price * quantity;
                    tax = total * 0.12;
                    totalAmount = total + tax;


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalAmount;
    }

    public static int getTotalSoldProducts() {
        int totalQty = 0;

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) return 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell quantityCell = row.getCell(5);

                if (quantityCell != null && quantityCell.getCellType() == CellType.NUMERIC) {
                    totalQty += (int) quantityCell.getNumericCellValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalQty;
    }

}
