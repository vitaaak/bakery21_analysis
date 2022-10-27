package sample.entity;


public class Products {

    private String categoryName;
    private String productName;
    private double price;
    private int kcal;
    private int weight;

    private static String staticName;


    public Products(){}
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {return productName;}

    public void setProductName(String productName) {this.productName = productName;}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) { this.weight = weight; }

    public String toString() {
        return "Product{" +
                "categoryName='" + categoryName + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", kcal='" + kcal + '\'' +
                ", weight=" + weight + '\'' +
                '}';
    }

    public static String getStaticName(){
        return staticName;
    }

    public static void setStaticName(String staticName){
        Products.staticName = staticName;
    }
}
