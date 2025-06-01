import java.util.ArrayList;
import java.util.List;

public class PurchaseService {
    private Shop shop;
    private static InputOutput io = null;
    private static CartManager cartManager = null;

    public PurchaseService(Shop shop, InputOutput io, CartManager cartManager) {
        this.shop = shop;
        this.io = io;
        this.cartManager = cartManager;
    }
    public ShoppingCart acquisition(){
        ShoppingCart cart = new ShoppingCart();
        List<Product> products = new ArrayList<>();
        cart.setShop(shop);

        while (true) {
            System.out.println("\n=== Меню корзины ===");
            System.out.println("1. Добавить товар в корзину");
            System.out.println("2. Удалить товар из корзины");
            System.out.println("3. Показать весь товар в корзине");
            System.out.println("4. Завершить покупки (end)");
            System.out.print("Выберите действие: ");

            String action = io.readLine();
            if (action.equals("4")) {
                cart.setBuyThisStuff(products);
                return cart;
            }

            switch (action) {
                case "1":
                    products = cartManager.addProduct(products);
                    break;
                case "2":
                    products = cartManager.removeProduct(products);
                    break;
                case "3":
                    cartManager.displayProducts(products);
                    break;
            }
        }

    }
}
