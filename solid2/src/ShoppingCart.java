import java.util.List;
import java.util.UUID;

public class ShoppingCart {

    private UUID buyerID;
    private Shop shop;
    private List<Product> buyThisStuff;

    public ShoppingCart(UUID buyerID, Shop shop, List<Product> buyThisStuff) {
        this.buyerID = buyerID;
        this.shop = shop;
        this.buyThisStuff = buyThisStuff;
    }

    public UUID getBuyerID() {
        return buyerID;
    }

    public Shop getShop() {
        return shop;
    }

    public List<Product> getBuyThisStuff() {
        return buyThisStuff;
    }

    public void setBuyerID(UUID buyerID) {
        this.buyerID = buyerID;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setBuyThisStuff(List<Product> buyThisStuff) {
        this.buyThisStuff = buyThisStuff;
    }


}
