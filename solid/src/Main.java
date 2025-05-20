import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Customer customer = null;
    static Shop shop = null;
    static Shop shopResult = null;
    public static void main(String[] args) {
        String[] optionsStart = {
                "1. Заполнить Био о Себе",
                "2. Выбрать ближайший магазин",
                "3. Показать список товаров в данном магазине",
                "4. Приобрести товар",
                "5. Показать мою корзину",
                "6. Удалить товар из корзины",
                "7. Очистить всю корзину",
                "8. Выход"
        };

        Manufacture manufactureApply = new Manufacture(
                "Apple",
                "USA"
        );
        Manufacture manufactureSamsung = new Manufacture(
                "Korea",
                "Barnaul"
        );
        Product product1 = new Product(
                UUID.randomUUID(),
                "phone",
                "Apple aphone 10",
                100.10,
                manufactureApply
        );
        Product product2 = new Product(
                UUID.randomUUID(),
                "phone",
                "Samsung A10",
                10.10,
                manufactureSamsung
        );
        List<Product> listProductShopDiksi = new ArrayList<>();
        listProductShopDiksi.add(product1);
        listProductShopDiksi.add(product2);
        Shop shopDiksi = new ShopDiksi(
                listProductShopDiksi,
                new Address(
                        "RU",
                        "Moscow",
                        "Big Street"
                )
        );
        Shop shopDiksi2 = new ShopDiksi(
                listProductShopDiksi,
                new Address(
                        "RU",
                        "Moscow",
                        "Small Street"
                )
        );


        System.out.println(shopDiksi.toString());

        int indexResponse = -1;
        while (true) {

            indexResponse = showMenu(optionsStart);
            if (indexResponse == 8) {
                break;
            } else {
                possiblePurchaseOption(indexResponse);
            }
        }

    }

    private static int showMenu(String[] options) {
        System.out.println("Выберите вариант:");
        for (String option : options) {
            System.out.println(option);
        }
        return Integer.parseInt(scanner.nextLine());
    }


    public static String possiblePurchaseOption(int position) {
        switch (position){
            case 1:
                fillBio();
                break;
            case 2:
                shop =  nearestShop();
                break;
            case 3:
                listAllProduct(shop);
                break;

        }

        return null;
    }

    private static void listAllProduct(Shop shop) {
        shop.showProduct();

    }


    public static Shop nearestShop() {
        System.out.println("Выберите один из ближайщих магазинов");
        System.out.println("Расположены на Вашей улице: ");
        ShopDiksi.getAllShope().stream()
                .filter(s -> s.getAddress().getStreet().equals(customer.getAddress().getStreet()))
                .forEach(shop -> {
                    System.out.println(shop);
                    System.out.print("Вам подходит этот магазин? (да/нет): ");
                    String userAnswer = scanner.nextLine().trim().toLowerCase();
                    if (userAnswer.equals("да")){
                        shopResult = shop;
                    }
                });
        if(shopResult == null) {
            System.out.println("Расположены в Вашем городе: ");
            ShopDiksi.getAllShope().stream()
                    .filter(s -> s.getAddress().getCity().equals(customer.getAddress().getCity()))
                    .forEach(shop ->
                    {
                        System.out.println(shop);
                        System.out.print("Вам подходит этот магазин? (да/нет): ");
                        String userAnswer = scanner.nextLine().trim().toLowerCase();
                        if (userAnswer.equals("да")) {
                            shopResult = shop;
                        }
                    });
        }
        if(shopResult == null) {
            System.out.println("Расположены в Вашей стране: ");
            ShopDiksi.getAllShope().stream()
                    .filter(s -> s.getAddress().getCountry().equals(customer.getAddress().getCountry()))
                    .forEach(System.out::println);
        }
        return shopResult;
    }

    private static void fillBio() {
        String tempCustomer = null;
        List<String> tempCustomerAddress = new ArrayList<>();
        System.out.println("Заполните БИО о себе, все поля важны: ");
        System.out.print("Введите Ваш Имя: ");
        tempCustomer = scanner.nextLine();
        System.out.println("Введите Ваш адрес:");
        System.out.print("Ваша страна: ");
        tempCustomerAddress.add(scanner.nextLine());
        System.out.print("Ваш Город: ");
        tempCustomerAddress.add(scanner.nextLine());
        System.out.print("Ваша улица, проспект: ");
        tempCustomerAddress.add(scanner.nextLine());
        // create new customer
        customer = new Customer(
                tempCustomer,
                new Address(
                        tempCustomerAddress.get(0),
                        tempCustomerAddress.get(1),
                        tempCustomerAddress.get(2)
                )
        );


    }
}