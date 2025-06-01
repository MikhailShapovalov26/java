import java.sql.Timestamp;

public class ProductDelivery {

    private Address addressDelivery;
    private ShoppingCart shoppingCartBuyer;
    private Timestamp timestamp;

    public ProductDelivery( Address addressDelivery, ShoppingCart shoppingCartBuyer, Timestamp timestamp) { //Все поля обязательн
        this.addressDelivery = addressDelivery;
        this.shoppingCartBuyer = shoppingCartBuyer;
        this.timestamp = timestamp;
    }
public ProductDelivery(){

}

    public Address getAddressDelivery() {
        return addressDelivery;
    }

    public ShoppingCart getShoppingCartBuyer() {
        return shoppingCartBuyer;
    }


    public void setAddressDelivery(Address addressDelivery) {
        this.addressDelivery = addressDelivery;
    }

    public void setShoppingCartBuyer(ShoppingCart shoppingCartBuyer) {
        this.shoppingCartBuyer = shoppingCartBuyer;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Доставка товара будет осуществленна из магазина " +
                shoppingCartBuyer.getShop().getName() +
                "\n" +
                ", по адресу " + addressDelivery +
                "\n" +
                ", товар который будет доставлен " +
                shoppingCartBuyer.getBuyThisStuff().toString() +
                "\n" +
                ", в период " + timestamp;
    }
}
