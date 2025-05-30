import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PurchaseService {
    public static ShoppingCart acquisition(Shop shop) throws InterruptedException {
        ShoppingCart resultshoppingCart = new ShoppingCart();
        List<Product> products = new ArrayList<>();
        resultshoppingCart.setShop(shop);
        while (true) {
            System.out.println("\n=== Меню корзины ===");
            System.out.println("1. Добавить товар в корзину");
            System.out.println("2. Удалить товар из корзины");
            System.out.println("3. Показать весь товар в корзине");
            System.out.println("4. Завершить покупки (end)");
            System.out.print("Выберите действие: ");

            Scanner scanner = new Scanner(System.in);
            String action = scanner.nextLine();

            if (action.equals("4") || action.equalsIgnoreCase("end")){
                resultshoppingCart.setBuyThisStuff(products);
                return resultshoppingCart;
            }
            System.out.println("\nДоступные товары:");
            for (int i = 0; i < shop.getProducts().size(); i++) {
                Product p = shop.getProducts().get(i);
                System.out.printf("%d. %s - %.2f руб. (Остаток: %.2f)%n",
                        i + 1, p.getName(), p.getPrice(), p.getCount());
            }

            if(action.equals("1")) {
                System.out.print("Выберите номер товара: ");
                String productChoice = scanner.nextLine();
                System.out.print("Укажите количество: ");
                String quantityStr = scanner.nextLine();
                try {
                    Product tmpProduct = shop.getProducts().get(Integer.parseInt(productChoice) - 1);
                    System.out.println("Вы выбрали : " + tmpProduct.getName() + " " + tmpProduct.getPrice());
                    products.add(new Product(tmpProduct.getName(), tmpProduct.getPrice(), Double.parseDouble(quantityStr)));
                    shop.deleteProduct(tmpProduct, Double.parseDouble(quantityStr));
                    System.out.printf("Добавлено: %s x%.2f%n",
                            tmpProduct.getName(), Double.parseDouble(quantityStr));
                } catch
                (Exception e) {
                    System.out.println("Ошибка ввода!");
                }
            }else if(action.equals("2")){
                if (products.isEmpty()) {
                    System.out.println("Корзина пуста!");
                    continue;
                }
                System.out.println("\nТовары в корзине:");
                for (int i = 0; i < resultshoppingCart.getBuyThisStuff().size(); i++) {
                    Product p = resultshoppingCart.getBuyThisStuff().get(i);
                    System.out.printf("%d. %s - %.2f x %.2f%n",
                            i + 1, p.getName(), p.getPrice(), p.getCount());
                }
                System.out.print("Выберите номер товара для удаления: ");
                String removeChoice = scanner.nextLine();
                try {
                    int removeIndex = Integer.parseInt(removeChoice) - 1;
                    Product toRemove = resultshoppingCart.getBuyThisStuff().get(removeIndex);

                    shop.returnWarehouse(toRemove, toRemove.getCount());

                    // Удаляем из корзины
                    resultshoppingCart.getBuyThisStuff().remove(removeIndex);
                    System.out.println("Товар удален из корзины");

                } catch (Exception e) {
                    System.out.println("Ошибка ввода!");
                }
            }
            Thread.sleep(500); // Небольшая задержка для удобства чтения

        }
    }
}
