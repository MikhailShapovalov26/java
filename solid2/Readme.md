## Задание

Написать приложение которое бы выполняло весь этот функционал и 
показать примеру в коде такие как DRY, 4 из 5 принципов SOLID


    Вывод доступных для покупки товаров
    Фильтрация товаров по ключевым словам, ценам, производителям
    Составление продуктовой корзины пользователя
    Трекинг заказа в системе доставки
    Возврат заказа, повтороение заказа
    Система рейтинга для товаров
    Простая рекомендательная система для покупок


1) Вывод доступных для покупки товаров
Реализован в классе Shop


```java
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
```
** метод formatProductList()

2) Фильтрация товаров по ключевым словам, ценам, производителям
Для фильтрации был написаны соответсвующий метод, который принимал на вход часть слова,
и возвращает спсок всех товаров, которые есть в данном магазине.

```java
 public static List<Product> filterProductName(Shop shop, String name){

        return shop.getProducts()
                .stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }
```
или пример по сравнению цен
```java
public static List<Product> filterProductCost(Shop shop, Double cost){

        return shop.getProducts()
                .stream()
                .filter(product -> product.getPrice() >= cost )
                .collect(Collectors.toList());
    }

```
Можно реализовать DRY в данном методе фильтрации, чтоб не писать 4 метода на разное сравнение,
можно передать `Predicate` это сделает этот метод более универсальным
Вот реализация
```java
public static List<Product> filterProductCost(Shop shop, Predicate<Product> filterPredicate){

        return shop.getProducts()
                .stream()
                .filter(filterPredicate)
                .collect(Collectors.toList());
    }
```
Ну и сама передача Predicate
```java
 shops.forEach(shop ->
    {
        System.out.println("В магазине " + shop.getName() + " можно купить сл товар");
        System.out.println(FilterProduct.filterProductCost (shop, product -> product.getPrice() > 90.0));
    }
);
```
То есть меня условие на необходимое, получаем другое сравнение, не дублируя код самого метода.

3) Составление продуктовой корзины пользователя

Bac

В данном примере я не использовал ни какие числа, а использовал `shop.getProducts().size()`
```java
    for (int i = 0; i < shop.getProducts().size(); i++) {
                System.out.print(i + 1 + ". - ");
                System.out.println(shop.getProducts().get(i));
            }
```
### Принципы SOLID

1.  Принцип это SRP Single Responsibility Principle - Принцип единственной отвественности

    В данном коде можно сказать. Что многие классы соответствуют данному принципу
К примеру:
1.1) ─ ShoppingCart должен отвечать только за корзину, а не за расчёт скидок.
```java
public class ShoppingCart {

    private UUID buyerID;
    private Shop shop;
    private List<Product> buyThisStuff;

    public ShoppingCart(Shop shop, List<Product> buyThisStuff) {
        this.buyerID = UUID.randomUUID();
        this.shop = shop;
        this.buyThisStuff = buyThisStuff;
    }
```
В данном классе нет ни каких дополнительных действий, ни рекомендательной системы, ни скидочной, ни сортировки, ни доставки, он отвечает только 
за потребительскую корзину
К примеру у меня есть такой класс как `GenerateData.java` он нарушает данный принцип, так как не только генерирует тестовые данный,
но и является своего рода базой данных для проверки возможности доставки товаров покупателю.

2. Второй принцип это Open/Closed Principle (OCP) - Принцип открытости/закрытости

    Классы должны быть закрыты для изменений, но открыты для расширений

К примеру у меня есть класс `FilterProduct.java` он отвечает за сортировку товара по name и цене,
так вот сортировка по цене реализована 
```java
    public static List<Product> filterProductCost(Shop shop, Predicate<Product> filterPredicate){

        return shop.getProducts()
                .stream()
                .filter(filterPredicate)
                .collect(Collectors.toList());
    }
```
То есть если мы передадим Predicate с другим условием, не которые в коде, то сортировка будет работать на других условиях, нам не потребуется 
менять методы в самом классе.
Вот пример обращения
`System.out.println(FilterProduct.filterProductCost(resultShop, product -> product.getPrice() > cost));`
Если мы изменим условия сравнения, то метод отработает.
3. Liskov Substitution Principle (LSP) – Принцип подстановки Барбары Лисков

    Подклассы должны заменять своих родителей без изменения поведения программы.
Для данного принципа я реализовал `DiscountProduct` который не будет ломать логику, если его использовать вместо `Product`
```java
public class DiscountProduct  extends Product{
    private double discount;
    
    public DiscountProduct(String name, double price, double count, double discount) {
        super(name, price, count);
        this.discount = discount;
    }
    @Override
    public double getPrice() {
        return super.getPrice() * (1 - discount / 100); // Применяем скидку
    }
}
```
4. Interface Segregation Principle (ISP) - Принцип распределения интерфейсов
   Клиенты не должны зависеть от методов, которые они не используют.
5. Dependecy Inversion Principle(DIP) - принцип инверсии зависимостей
   Модули высокого уровня не должны зависеть от модулей низкого уровня. Оба должны зависеть от абстракций.
Для реализации этого принципа я переписал класс `PurchaseService`, старый класс который был называется `PurchaseService_old`, теперь `PurchaseService` зависит
от абстракий (InputOutput, CartManager)
