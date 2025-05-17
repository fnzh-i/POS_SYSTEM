import java.util.ArrayList;
import java.util.List;

public class OrderData {
    int orderId;
    String date;
    double total;
    List<String> productNames;

    public OrderData(int orderId, String date) {
        this.orderId = orderId;
        this.date = date;
        this.total = 0;
        this.productNames = new ArrayList<>();
    }

    public void addProduct(String product, double subtotal) {
        productNames.add(product);
        total += subtotal;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public List<String> getProductNames() {
        return productNames;
    }
}
