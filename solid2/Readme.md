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