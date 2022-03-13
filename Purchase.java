package budget;


public class Purchase {

    String name;
    float price;
    String category;

    public Purchase(String name, float price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
