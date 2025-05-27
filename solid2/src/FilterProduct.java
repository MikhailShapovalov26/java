import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterProduct {

    public static List<Product> filterProductName(Shop shop, String name){

        return shop.getProducts()
                .stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }
    public static List<Product> filterProductCost(Shop shop, Predicate<Product> filterPredicate){

        return shop.getProducts()
                .stream()
                .filter(filterPredicate)
                .collect(Collectors.toList());
    }
}
