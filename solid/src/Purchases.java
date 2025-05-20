import java.util.List;

public class Purchases {

    private Customer customer;
    private Shop shop;
    private List<Product> backed;

    public Purchases(Customer customer, Shop shop, List<Product> backed) {
        this.customer = customer;
        this.shop = shop;
        this.backed = backed;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Shop getShop() {
        return shop;
    }

    public List<Product> getBacked() {
        return backed;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setBacked(List<Product> backed) {
        this.backed = backed;
    }


    // корзимна
    
}
