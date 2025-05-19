package POS_SYSTEM;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderData implements Comparable<OrderData> {
    private int orderId;
    private String date;
    private Date dateObj;  // For proper date comparison
    private List<String> productNames;
    private double total;
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a");

    public OrderData(int orderId, String date) {
        this.orderId = orderId;
        this.date = date;
        this.productNames = new ArrayList<>();
        this.total = 0.0;

        try {
            this.dateObj = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            this.dateObj = new Date();
            this.date = DATE_FORMAT.format(this.dateObj);
        }
    }

    public void addProduct(String productName, double productTotal) {
        this.productNames.add(productName);
        this.total += productTotal;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public String getDate() { return date; }
    public Date getDateObj() { return dateObj; }
    public List<String> getProductNames() { return productNames; }
    public double getTotal() { return total; }

    @Override
    public int compareTo(OrderData other) {
        // Sort by date in descending order (newest first)
        return other.getDateObj().compareTo(this.dateObj);
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "orderId=" + orderId +
                ", date='" + date + '\'' +
                ", total=" + total +
                ", products=" + productNames +
                '}';
    }
}