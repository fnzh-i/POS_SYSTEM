package POS_SYSTEM;

public class Product {
    private String name;
    private String size;
    private String image;
    private double price;
    private String category;
    private final int itemId;

    public Product(String name, String size, String image, double price, String category, int itemId) {
        this.name = name;
        this.size = size;
        this.image = image;
        this.price = price;
        this.category = category;
        this.itemId = itemId;
    }

    // Getters
    public String getName() { return name; }
    public String getSize() { return size; }
    public String getImage() { return image; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getItemId() { return itemId; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setSize(String size) { this.size = size; }
    public void setImage(String image) { this.image = image; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
}