import java.util.ArrayList;
import java.util.List;

public class ShopDiksi implements Shop {
    private static final List<ShopDiksi> allShope = new ArrayList<>();
    private List<Product> listProduct;
    private Address address;


    public ShopDiksi(List<Product> listProduct, Address address) {
        this.listProduct = listProduct;
        this.address = address;
        allShope.add(this);
    }

    public static List<ShopDiksi> getAllShope() {
        return new ArrayList<>(allShope);
    }

    public List<Product> getListProduct() {
        return listProduct;
    }


    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public List<Product> showProduct() {
        return this.listProduct;
    }

    @Override
    public List<Product> filterProduct(String name) {
        List<Product> tempList = new ArrayList<>();
        listProduct.forEach(e ->
        {

            if (e.getName().equals(name)) {
                tempList.add(e);
            }
        });

        return tempList;
    }

    @Override
    public List<Product> filterProduct(double price) {
        List<Product> tempList = new ArrayList<>();
        listProduct.forEach(e ->
        {
            if (e.getPrice() == price) {
                tempList.add(e);
            }
        });
        return tempList;
    }

    @Override
    public List<Product> filterProduct(Manufacture manufacture){
        List<Product> tempList = new ArrayList<>();
        listProduct.forEach(e ->
        {
            if (e.getManufacture() == manufacture) {
                tempList.add(e);
            }
        });
        return tempList;
    }

    @Override
    public boolean addProduct(Product product) {
        listProduct.add(product);
        return true;
    }

    @Override
    public boolean buyProduct(Product product) {
        listProduct.remove(product);
        // Если кол-во 9 то мы долждны изменить на 8
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("В магазине по адресу \n").append(address.toString()).append("Можно купить следующий товар:\n").append("Список продуктов:\n");
               listProduct.forEach(
                       e -> {
                           stringBuilder.append("\n");
                           stringBuilder.append(e.getName());
                       }
               );
        return stringBuilder.toString();
    }

}
