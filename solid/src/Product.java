import java.util.UUID;

public class Product {

    private UUID productUUID;
    private String name;
    private String description;
    private double price;
    private Manufacture manufacture;

    public Product(UUID productUUID, String name, String description, double price, Manufacture manufacture) {
        this.productUUID = productUUID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.manufacture = manufacture;
    }

    public UUID getProductUUID() {
        return productUUID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setProductUUID(UUID productUUID) {
        this.productUUID = productUUID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getPrice() {
        return price;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }
}
