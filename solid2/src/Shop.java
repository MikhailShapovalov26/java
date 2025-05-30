import java.util.List;

public class Shop {

    private String name;
    private Address address;
    private List<Product> products;


    public Shop(String name, Address address, List<Product> products) {
        this.name = name;
        this.address = address;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Название магазина: '" + name + '\'' +
                '\n' +
                "   Адрес магазина: " + address +
                '\n' +
                "   Доступные продукты: \n" + formatProductList();
    }

    public String formatProductList() {
        if (products.isEmpty()) {
            return "Нет доступных продуктов";
        }

        StringBuilder sb = new StringBuilder();
        products.forEach(p -> sb.append("   - ").append(p).append("\n"));
        return sb.toString();
    }

    public void deleteProduct(Product product, double purchased) {
        if (purchased > product.getCount()) {
            System.out.println("Не достаточное кол-во товара в данном магазине");
            return;
        }
        product.setCount(product.getCount() - purchased);
    }

    public void returnWarehouse(Product product, double purchased) {
        product.setCount(product.getCount() + purchased);

    }
}
