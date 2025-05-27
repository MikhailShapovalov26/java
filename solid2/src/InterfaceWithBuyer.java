import java.util.List;
import java.util.Scanner;

public class InterfaceWithBuyer {

    static Scanner scanner = new Scanner(System.in);

    public static void greeting(List<Shop> shops) throws Exception {
        String[] optionsStart = {
                "1. Выбрать магазин",
                "2. Показать список товаров в данном магазине",
                "3. Отфильтровать необходимый товар",
                "4. Приобрести товар",
                "5. Показать мою корзину",
                "6. Удалить товар из корзины",
                "7. Очистить всю корзину",
                "8. Выход"
        };

        boolean isRunning = true;
        Shop resultShop = null;
        while (isRunning) {
            System.out.println("Выберите вариант:");
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
                    switch (Integer.parseInt(scanner.nextLine())){
                        case 1:
                            System.out.println("Необходимо вести цену");
                            int cost = Integer.parseInt(scanner.nextLine());
                            System.out.println(FilterProduct.filterProductCost (resultShop, product -> product.getPrice() > cost));
                            break;
                        case 2:
                            System.out.println("Необходимо вести название");
                            String name  = scanner.nextLine();
                            System.out.println(FilterProduct.filterProductName(resultShop, name));
                            break;

                    }
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                default:
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
