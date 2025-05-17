import java.awt.*;

public class Product extends Point {
    private final String name;
    private final String size;
    private final String image;
    private final double price;
    private final String category;
    private int  itemId;
    private int Quantity;

    public Product(String name, String size, String image, double price, String category, int  itemId) {
        this.name = name;
        this.size = size;
        this.image = image;
        this.price = price;
        this.category = category;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return (int) price;
    }

    public String getCategory() {
        return category;
    }

    public int getId() {

        return itemId;
    }

    public int getQuantity() {
        return Quantity;
    }
}

