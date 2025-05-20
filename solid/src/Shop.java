import java.util.List;

public interface Shop {
    List<Product> showProduct();
    List<Product> filterProduct(String name);
    List<Product> filterProduct(double price);
    List<Product> filterProduct(Manufacture manufacture);
    boolean addProduct(Product product);
    boolean buyProduct(Product product);
    String toString();
}
