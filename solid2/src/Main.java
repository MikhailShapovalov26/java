import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        List<Product> products;
        List<Shop> shops;

        products = GenerateData.generateDataProduct();

        for (Product product : products) {
            System.out.println(product.toString());
        }
        shops = GenerateData.generateShop(products);

        shops.forEach(System.out::println);

        shops.forEach(shop ->
                {
                    System.out.println("В магазине " + shop.getName() + " можно купить сл товар");
                    System.out.println(FilterProduct.filterProductName(shop, "p"));
                }
        );
        shops.forEach(shop ->
                {
                    System.out.println("В магазине " + shop.getName() + " можно купить сл товар");
                    System.out.println(FilterProduct.filterProductCost (shop, product -> product.getPrice() > 91.0));
                }
        );

        InterfaceWithBuyer.greeting(shops);


    }
}