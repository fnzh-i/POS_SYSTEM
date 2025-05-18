package POS_SYSTEM;

import java.util.ArrayList;
import java.util.List;

public class OrderData {
    private int orderId;
    private String date;
    private List<String> productNames;
    private double total;

    public OrderData(int orderId, String date) {
        this.orderId = orderId;
        this.date = date;
        this.productNames = new ArrayList<>();
        this.total = 0.0;
    }

    public void addProduct(String productName, double productTotal) {
        this.productNames.add(productName);
        this.total += productTotal;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public String getDate() { return date; }
    public List<String> getProductNames() { return productNames; }
    public double getTotal() { return total; }
}
