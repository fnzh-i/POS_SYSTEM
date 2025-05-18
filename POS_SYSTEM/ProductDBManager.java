package POS_SYSTEM;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ProductDBManager {
    private static final String FILE_PATH = "db/productItems.xlsx";
    private static final String SHEET_NAME = "Products";

    private static final int COL_ID = 0;
    private static final int COL_NAME = 1;
    private static final int COL_SIZE = 2;
    private static final int COL_IMAGE = 3;
    private static final int COL_PRICE = 4;
    private static final int COL_CATEGORY = 5;

    static {
        initializeDatabase();
    }

    private static void initializeDatabase() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet(SHEET_NAME);

                Row headerRow = sheet.createRow(0);
                headerRow.createCell(COL_ID).setCellValue("itemID");
                headerRow.createCell(COL_NAME).setCellValue("Name");
                headerRow.createCell(COL_SIZE).setCellValue("Size");
                headerRow.createCell(COL_IMAGE).setCellValue("Image");
                headerRow.createCell(COL_PRICE).setCellValue("Price");
                headerRow.createCell(COL_CATEGORY).setCellValue("Category");

                file.getParentFile().mkdirs();

                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                }
            } catch (IOException e) {
                System.err.println("Error initializing database: " + e.getMessage());
            }
        }
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                System.err.println("Sheet '" + SHEET_NAME + "' not found");
                return products;
            }

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                try {
                    // Handle each cell with null checks
                    int id = (int) getNumericCellValue(row.getCell(COL_ID), 0);
                    String name = getStringCellValue(row.getCell(COL_NAME), "");
                    String size = getStringCellValue(row.getCell(COL_SIZE), "");
                    String image = getStringCellValue(row.getCell(COL_IMAGE), "");
                    double price = getNumericCellValue(row.getCell(COL_PRICE), 0.0);
                    String category = getStringCellValue(row.getCell(COL_CATEGORY), "");

                    // Only add product if required fields are present
                    if (!name.isEmpty() && !category.isEmpty()) {
                        products.add(new Product(name, size, image, price, category, id));
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing row " + r + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading products: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    private static String getStringCellValue(Cell cell, String defaultValue) {
        if (cell == null) return defaultValue;
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    return String.valueOf((int)cell.getNumericCellValue());
                default:
                    return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static double getNumericCellValue(Cell cell, double defaultValue) {
        if (cell == null) return defaultValue;
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING:
                    try {
                        return Double.parseDouble(cell.getStringCellValue().trim());
                    } catch (NumberFormatException e) {
                        return defaultValue;
                    }
                default:
                    return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void addProduct(Product product) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);

            // Find first empty row
            int newRowNum = findFirstEmptyRow(sheet);

            if (newRowNum == -1) {
                // If no empty rows found, add at the end
                newRowNum = sheet.getLastRowNum() + 1;
            }

            Row row = sheet.createRow(newRowNum);

            // Create all cells explicitly
            row.createCell(COL_ID).setCellValue(newRowNum);
            row.createCell(COL_NAME).setCellValue(product.getName());
            row.createCell(COL_SIZE).setCellValue(product.getSize());
            row.createCell(COL_IMAGE).setCellValue(product.getImage());
            row.createCell(COL_PRICE).setCellValue(product.getPrice());
            row.createCell(COL_CATEGORY).setCellValue(product.getCategory());

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int findFirstEmptyRow(XSSFSheet sheet) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(COL_NAME) == null ||
                    row.getCell(COL_NAME).getStringCellValue().isEmpty()) {
                return i;
            }
        }
        return -1; // No empty rows found
    }

    public static void updateProduct(Product product) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
            int rows = sheet.getLastRowNum();

            for (int r = 1; r <= rows; r++) {
                Row row = sheet.getRow(r);
                if (row != null && (int) getNumericCellValue(row.getCell(COL_ID), -1) == product.getItemId()) {
                    // Ensure cells exist before setting values
                    if (row.getCell(COL_NAME) == null) row.createCell(COL_NAME);
                    if (row.getCell(COL_SIZE) == null) row.createCell(COL_SIZE);
                    if (row.getCell(COL_IMAGE) == null) row.createCell(COL_IMAGE);
                    if (row.getCell(COL_PRICE) == null) row.createCell(COL_PRICE);
                    if (row.getCell(COL_CATEGORY) == null) row.createCell(COL_CATEGORY);

                    row.getCell(COL_NAME).setCellValue(product.getName());
                    row.getCell(COL_SIZE).setCellValue(product.getSize());
                    row.getCell(COL_IMAGE).setCellValue(product.getImage());
                    row.getCell(COL_PRICE).setCellValue(product.getPrice());
                    row.getCell(COL_CATEGORY).setCellValue(product.getCategory());
                    break;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int productId) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
            int rows = sheet.getLastRowNum();

            for (int r = 1; r <= rows; r++) {
                Row row = sheet.getRow(r);
                if (row != null && (int) getNumericCellValue(row.getCell(COL_ID), -1) == productId) {
                    sheet.removeRow(row);
                    if (r < rows) {
                        sheet.shiftRows(r + 1, rows, -1);
                    }
                    break;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

    public static Product getProductById(int productId) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) return null;

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row != null && (int) getNumericCellValue(row.getCell(COL_ID), -1) == productId) {
                    String name = getStringCellValue(row.getCell(COL_NAME), "");
                    String size = getStringCellValue(row.getCell(COL_SIZE), "");
                    String image = getStringCellValue(row.getCell(COL_IMAGE), "");
                    double price = getNumericCellValue(row.getCell(COL_PRICE), 0.0);
                    String category = getStringCellValue(row.getCell(COL_CATEGORY), "");

                    if (!name.isEmpty() && !category.isEmpty()) {
                        return new Product(name, size, image, price, category, productId);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error finding product: " + e.getMessage());
        }
        return null;
    }
}