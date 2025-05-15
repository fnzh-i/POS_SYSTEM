package POS_SYSTEM;

public class Product {
    private final String name;
    private final String size;
    private final String image;
    private final double price;
    private final String category;
    int itemId;
    public Product(String name, String size, String image, double price, String category, int itemId) {
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

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}

