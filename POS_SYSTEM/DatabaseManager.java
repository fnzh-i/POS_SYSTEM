import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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


            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));

            Cell cell = row.createCell(0);
            cell.setCellValue(new Date());  // Set current date
            cell.setCellStyle(dateCellStyle);  // Apply date format



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

                // Get date cell and read based on type
                Cell dateCell = row.getCell(0);
                String date;

                if (dateCell == null) {
                    date = "Unknown";
                } else if (dateCell.getCellType() == CellType.STRING) {
                    date = dateCell.getStringCellValue();
                } else if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                    Date dateValue = dateCell.getDateCellValue();
                    date = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(dateValue);
                } else {
                    date = "Unknown";
                }

                int orderId = (int) row.getCell(1).getNumericCellValue();
                String productName = row.getCell(2).getStringCellValue();
                double price = row.getCell(4).getNumericCellValue();
                int quantity = (int) row.getCell(5).getNumericCellValue();

                double vat = 0.12;
                double totalAmount;
                double tax;

                double total = price * quantity;
               tax = vat * total;
               totalAmount  = tax + total;



                orderMap.putIfAbsent(orderId, new OrderData(orderId, date));
                orderMap.get(orderId).addProduct(productName, totalAmount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderMap;
    }
}
