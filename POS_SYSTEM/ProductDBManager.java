package POS_SYSTEM;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ProductDBManager {
    private static final String FILE_PATH = "db/productItems.xlsx";
    private static final String SHEET_NAME = "Products";

    // Column indices
    private static final int COL_ID = 0;
    private static final int COL_NAME = 1;
    private static final int COL_SIZE = 2;
    private static final int COL_IMAGE = 3;
    private static final int COL_PRICE = 4;
    private static final int COL_CATEGORY = 5;

    // Initialize database with headers if it doesn't exist
    static {
        initializeDatabase();
    }

    private static void initializeDatabase() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet(SHEET_NAME);

                // Create header row
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(COL_ID).setCellValue("ID");
                headerRow.createCell(COL_NAME).setCellValue("Name");
                headerRow.createCell(COL_SIZE).setCellValue("Size");
                headerRow.createCell(COL_IMAGE).setCellValue("Image");
                headerRow.createCell(COL_PRICE).setCellValue("Price");
                headerRow.createCell(COL_CATEGORY).setCellValue("Category");

                // Create directory if it doesn't exist
                file.getParentFile().mkdirs();

                // Write the workbook
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                }
            } catch (IOException e) {
                System.err.println("Error initializing database: " + e.getMessage());
            }
        }
    }

    // Create/Add a new product
    public static void addProduct(Product product) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
            int newRowNum = sheet.getLastRowNum() + 1;

            Row row = sheet.createRow(newRowNum);
            row.createCell(COL_ID).setCellValue(newRowNum); // Auto-increment ID
            row.createCell(COL_NAME).setCellValue(product.getName());
            row.createCell(COL_SIZE).setCellValue(product.getSize());
            row.createCell(COL_IMAGE).setCellValue(product.getImage());
            row.createCell(COL_PRICE).setCellValue(product.getPrice());
            row.createCell(COL_CATEGORY).setCellValue(product.getCategory());

            // Save changes
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }

    // Read all products
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
            int rows = sheet.getLastRowNum();

            // Start from 1 to skip header row
            for (int r = 1; r <= rows; r++) {
                Row row = sheet.getRow(r);
                if (row != null) {
                    int id = (int) row.getCell(COL_ID).getNumericCellValue();
                    String name = row.getCell(COL_NAME).getStringCellValue();
                    String size = row.getCell(COL_SIZE).getStringCellValue();
                    String image = row.getCell(COL_IMAGE).getStringCellValue();
                    double price = row.getCell(COL_PRICE).getNumericCellValue();
                    String category = row.getCell(COL_CATEGORY).getStringCellValue();

                    products.add(new Product(name, size, image, price, category, id));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading products: " + e.getMessage());
        }

        return products;
    }

    // Update a product
    public static void updateProduct(Product product) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
            int rows = sheet.getLastRowNum();

            // Find the product by ID
            for (int r = 1; r <= rows; r++) {
                Row row = sheet.getRow(r);
                if (row != null && (int) row.getCell(COL_ID).getNumericCellValue() == product.itemId) {
                    row.getCell(COL_NAME).setCellValue(product.getName());
                    row.getCell(COL_SIZE).setCellValue(product.getSize());
                    row.getCell(COL_IMAGE).setCellValue(product.getImage());
                    row.getCell(COL_PRICE).setCellValue(product.getPrice());
                    row.getCell(COL_CATEGORY).setCellValue(product.getCategory());
                    break;
                }
            }

            // Save changes
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    // Delete a product
    public static void deleteProduct(int productId) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
            int rows = sheet.getLastRowNum();

            // Find the product by ID
            for (int r = 1; r <= rows; r++) {
                Row row = sheet.getRow(r);
                if (row != null && (int) row.getCell(COL_ID).getNumericCellValue() == productId) {
                    sheet.removeRow(row);

                    // Shift rows up after deletion
                    if (r < rows) {
                        sheet.shiftRows(r + 1, rows, -1);
                    }
                    break;
                }
            }

            // Save changes
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

    // Helper method to get product by ID
    public static Product getProductById(int productId) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
            int rows = sheet.getLastRowNum();

            for (int r = 1; r <= rows; r++) {
                Row row = sheet.getRow(r);
                if (row != null && (int) row.getCell(COL_ID).getNumericCellValue() == productId) {
                    String name = row.getCell(COL_NAME).getStringCellValue();
                    String size = row.getCell(COL_SIZE).getStringCellValue();
                    String image = row.getCell(COL_IMAGE).getStringCellValue();
                    double price = row.getCell(COL_PRICE).getNumericCellValue();
                    String category = row.getCell(COL_CATEGORY).getStringCellValue();

                    return new Product(name, size, image, price, category, productId);
                }
            }
        } catch (IOException e) {
            System.err.println("Error finding product: " + e.getMessage());
        }

        return null;
    }
}
