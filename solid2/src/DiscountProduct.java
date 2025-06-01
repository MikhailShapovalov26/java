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
