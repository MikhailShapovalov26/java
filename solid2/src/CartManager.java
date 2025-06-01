import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CartManager {
    private final Shop shop;
    private final InputOutput io;

    public CartManager(Shop shop, InputOutput io) {
        this.shop = shop;
        this.io = io;
    }

    public List<Product> addProduct(List<Product> products) {
        System.out.println("\nДоступные товары:");
        for (int i = 0; i < shop.getProducts().size(); i++) {
            Product p = shop.getProducts().get(i);
            System.out.printf("%d. %s - %.2f руб. (Остаток: %.2f)%n",
                    i + 1, p.getName(), p.getPrice(), p.getCount());
        }
        System.out.print("Выберите номер товара: ");
        String productChoice = io.readLine();
        System.out.print("Укажите количество: ");
        String quantityStr = InterfaceWithBuyer.scanner.nextLine();
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
        return products;
    }
    public List<Product> removeProduct(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("Корзина пуста!");
            return null;
        }
        System.out.println("\nТовары в корзине:");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s - %.2f x %.2f%n",
                    i + 1, p.getName(), p.getPrice(), p.getCount());
        }
        System.out.print("Выберите номер товара для удаления: ");
        String removeChoice = InterfaceWithBuyer.scanner.nextLine();
        try {
            int removeIndex = Integer.parseInt(removeChoice) - 1;
            Product toRemove = products.remove(removeIndex);
            shop.returnWarehouse(FilterProduct.filterProductName(shop,toRemove.getName()).get(0), toRemove.getCount());
            System.out.println("Товар удален из корзины и возвращен на склад");
            System.out.println();

        } catch (Exception e) {
            System.out.println("Ошибка ввода!");
        }
        return products;
    }
    public void displayProducts(List<Product> products) {
        AtomicInteger counter = new AtomicInteger(0);
        products.forEach(p -> {
            System.out.printf("%d. %s - %.2f x %.2f%n",
                    counter.addAndGet(1), p.getName(), p.getPrice(), p.getCount());
        });
    }
}