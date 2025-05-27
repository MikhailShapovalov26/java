import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateData {



    public static List<Product> generateDataProduct(){

        double min = 10;
        double max = 101;

        List<Product> resultProducts = new ArrayList<>();

        List<String> listProducts = new ArrayList<>();
        listProducts.add("phone");
        listProducts.add("notepad");
        listProducts.add("car");
        listProducts.add("cost");
        listProducts.add("batterer");
        listProducts.add("book");
        listProducts.add("headphone");
        listProducts.add("keyboard");
        listProducts.forEach(p -> {
            resultProducts.add(new Product(
                    p,
                    Math.round(ThreadLocalRandom.current().nextDouble(min, max)),
                    ThreadLocalRandom.current().nextInt((int) Math.round(min), (int) Math.round(max) + 1)
            ));
        });

        return resultProducts;
    }

    public static Address generateAddress(){

        List<String> listCountry = new ArrayList<>();
        listCountry.add("RU");
        listCountry.add("USA");
        listCountry.add("China");
        listCountry.add("Brazil");
        listCountry.add("Botch");
        listCountry.add("Poland");

        List<String> listIndexes = new ArrayList<>();
        listIndexes.add("123455");
        listIndexes.add("123424455");

        List<String> listCites= new ArrayList<>();
        listCites.add("Москва");
        listCites.add("Санкт-Петербург");
        listCites.add("Пекин");

        Random rand = new Random();

        return new Address(
                listCountry.get(rand.nextInt(listCountry.size())),
                listIndexes.get(rand.nextInt(listIndexes.size())),
                listCites.get(rand.nextInt(listCites.size())),
                "street"
        );

    }

    public static List<Shop> generateShop(List<Product> productList) {
        List<String> nameShop = new ArrayList<>();
        List<Shop> resultList = new ArrayList<>();
        nameShop.add("Магнит");
        nameShop.add("Дикси");
        nameShop.add("Жасмин");

        nameShop.forEach(name ->
        {
            resultList.add(
                    new Shop(
                            name,
                            generateAddress(),
                            productList
                    )
            );
        });
        return resultList;
    }

}
