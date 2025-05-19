package POS_SYSTEM;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class orderHistoryDBManager {
    private static final String EXCEL_FILE_PATH = "db/orderHistory.xlsx";
    private static final SimpleDateFormat DISPLAY_DATE_FORMAT =
            new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a");
    private static final SimpleDateFormat DATE_ONLY_FORMAT =
            new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
    private static final double VAT_RATE = 0.12;

    public static void addProductToDatabase(String displayDate, int orderIdTimestamp,
                                            String productName, String productSize,
                                            double price, int quantity) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                sheet = workbook.createSheet();
            }

            Row row = sheet.createRow(sheet.getLastRowNum() + 1);

            // Store the human-readable display date
            row.createCell(0).setCellValue(displayDate);

            // Store the numeric timestamp ID
            row.createCell(1).setCellValue(orderIdTimestamp);

            // Other product information
            row.createCell(2).setCellValue(productName);
            row.createCell(3).setCellValue(productSize);
            row.createCell(4).setCellValue(price);
            row.createCell(5).setCellValue(quantity);

            try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                workbook.write(fos);
            }
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

                // Get date cell and read based on type
                Cell dateCell = row.getCell(0);
                String date;

                if (dateCell == null) {
                    date = "Unknown";
                } else if (dateCell.getCellType() == CellType.STRING) {
                    date = dateCell.getStringCellValue();
                } else if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                    Date dateValue = dateCell.getDateCellValue();
                    date = DISPLAY_DATE_FORMAT.format(dateValue);
                } else {
                    date = "Unknown";
                }

                int orderId = (int) row.getCell(1).getNumericCellValue();
                String productName = row.getCell(2).getStringCellValue();
                double price = row.getCell(4).getNumericCellValue();
                int quantity = (int) row.getCell(5).getNumericCellValue();

                double total = price * quantity;
                double totalAmount = total;

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
        String todayDateOnly = DATE_ONLY_FORMAT.format(new Date());

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

                String cellDateOnly = getDateOnlyFromCell(dateCell);
                if (cellDateOnly == null) continue;

                if (cellDateOnly.equals(todayDateOnly)) {
                    double price = priceCell.getNumericCellValue();
                    int quantity = (int) quantityCell.getNumericCellValue();
                    todaysIncome += price * quantity;
                    totalAmount = todaysIncome;
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
                    total += price * quantity;
                }
            }

            totalAmount = total;

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

    private static String getDateOnlyFromCell(Cell dateCell) {
        try {
            if (dateCell.getCellType() == CellType.STRING) {
                Date parsedDate = DISPLAY_DATE_FORMAT.parse(dateCell.getStringCellValue());
                return DATE_ONLY_FORMAT.format(parsedDate);
            } else if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                Date parsedDate = dateCell.getDateCellValue();
                return DATE_ONLY_FORMAT.format(parsedDate);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }



    public static Map<String, Double> getMonthlyIncome() {
        Map<String, Double> monthlyIncome = new LinkedHashMap<>();
        SimpleDateFormat monthFormat = new SimpleDateFormat("M"); // Month number (1-12)

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) return monthlyIncome;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell dateCell = row.getCell(0);
                Cell priceCell = row.getCell(4);
                Cell quantityCell = row.getCell(5);

                if (dateCell == null || priceCell == null || quantityCell == null) continue;

                try {
                    Date date;
                    if (dateCell.getCellType() == CellType.STRING) {
                        date = DISPLAY_DATE_FORMAT.parse(dateCell.getStringCellValue());
                    } else if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                        date = dateCell.getDateCellValue();
                    } else {
                        continue;
                    }

                    String monthKey = monthFormat.format(date);
                    double price = priceCell.getNumericCellValue();
                    int quantity = (int) quantityCell.getNumericCellValue();
                    double amount = price * quantity;

                    monthlyIncome.merge(monthKey, amount, Double::sum);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the months in numerical order (1-12)
        return monthlyIncome.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Integer::parseInt)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}