import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class InterfaceWithBuyer {

    static Scanner scanner = new Scanner(System.in);

    public static void greeting(List<Shop> shops) throws Exception {
        String[] optionsStart = {
                "1. Выбрать магазин",
                "2. Показать список товаров в данном магазине",
                "3. Отфильтровать необходимый товар",
                "4. Добавление(удаление) товара из корзины покупателя",
                "5. Доставка товара покупателю",
                "6. Выход"
        };

        boolean isRunning = true;
        Shop resultShop = null;
        ShoppingCart shoppingCart = new ShoppingCart();
        ProductDelivery productDelivery = new ProductDelivery();
        while (isRunning) {
            System.out.println("Выберите вариант: ");
            for (String option : optionsStart) {
                System.out.println(option);
            }
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1:
                    try {
                        resultShop = showListShop(shops);
                        if (resultShop == null) {
                            throw new Exception("Не выбран магазин, до свидания");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        isRunning = false;
                    }
                    break;
                case 2:
                    System.out.println(resultShop != null ? resultShop.formatProductList() : null);
                    Thread.sleep(2000);
                    break;
                case 3:
                    System.out.println("Весь доступный продукт можно посмотреть на кнопку 2 в интерфейсе");
                    System.out.println("Чтоб отфильтровать весь товар в представленном магазине, выберите условия фильтрации");
                    System.out.println("1. По цене товара");
                    System.out.println("2. По названию товара");
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            System.out.println("Необходимо вести цену");
                            int cost = Integer.parseInt(scanner.nextLine());
                            System.out.println(FilterProduct.filterProductCost(resultShop, product -> product.getPrice() > cost));
                            break;
                        case 2:
                            System.out.println("Необходимо вести название");
                            String name = scanner.nextLine();
                            System.out.println(FilterProduct.filterProductName(resultShop, name));
                            break;

                    }
                    break;
                case 4:
                    InputOutput io = new ConsoleIO();
                    CartManager cartManager = new CartManager(resultShop, io);
                    PurchaseService purchaseService = new PurchaseService(resultShop, io, cartManager);
                    shoppingCart = purchaseService.acquisition();
                    productDelivery.setShoppingCartBuyer(shoppingCart);
                    break;
                case 5:
                    productDelivery.setShoppingCartBuyer(shoppingCart);
                    System.out.println("Для успешной доставки требуется указать Адрес и время");
                    System.out.println("Укажите страну");
                    String countryDelivery = scanner.nextLine();
                    if (!GenerateData.listCountry.contains(countryDelivery)) {
                        System.out.println("Такой страны нет в списке для доставки");
                        System.out.println(GenerateData.listCountry.toString());
                        break;
                    }
                    System.out.println("Укажите индекс");
                    String indexDelivery = scanner.nextLine();
                    if (!GenerateData.listIndexes.contains(indexDelivery)) {
                        System.out.println("Такой страны нет в списке для доставки");
                        System.out.println(GenerateData.listIndexes.toString());
                        break;
                    }
                    System.out.println("Укажите город");
                    String cityDelivery = scanner.nextLine();
                    if (!GenerateData.listCites.contains(cityDelivery)) {
                        System.out.println("Такой страны нет в списке для доставки");
                        System.out.println(GenerateData.listCites.toString());
                        break;
                    }
                    System.out.println("Укажите улицу");
                    String streetDelivery = scanner.nextLine();
                    Address addressDeliver = new Address(countryDelivery,
                            indexDelivery,
                            cityDelivery,
                            streetDelivery);
                    productDelivery.setAddressDelivery(addressDeliver);
                    System.out.println("Необходимо указать время для доставки в формате 'dd/MM/yyyy'");
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    productDelivery.setTimestamp(new Timestamp(formatter.parse(scanner.nextLine()).getTime()));

                    System.out.println();
                    System.out.println(productDelivery.toString());
                case 6:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Не допустимое значение");
            }
        }

    }

    public static Shop showListShop(List<Shop> shops) {
        System.out.println("Список доступных магазинов: ");
        return shops.stream()
                .peek(shop -> System.out.println(shop.getName()))
                .filter(shop -> {
                    System.out.println("Вам подходит данный магазин? (да/нет)");
                    return scanner.nextLine().equalsIgnoreCase("да");
                })
                .findFirst()
                .orElse(null);
    }
}
