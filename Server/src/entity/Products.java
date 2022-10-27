package entity;

public class Products {

    private String categoryName;
    private String productName;
    private double price;
    private int kcal;
    private int weight;

    public Products(String categoryName, String productName, double price, int kcal, int weight) {
        this.categoryName = categoryName;
        this.productName = productName;
        this.price = price;
        this.kcal = kcal;
        this.weight = weight;
    }
    public Products(){}


    public String getProductName() {
        return productName;
    }


    public double getPrice() {
        return price;
    }


    public int getKcal() {
        return kcal;
    }


    public int getWeight() {
        return weight;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
