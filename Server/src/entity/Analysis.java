package entity;

public class Analysis {

    public Analysis() {};

    private String alternative1;
    private String alternative2;
    private String alternative3;
    private String alternative4;

    private int scale;

    private double w1;
    private double w2;
    private double w3;
    private double w4;

    private int n;

    public int getScale() {
        return scale;
    }

    public String getAlternative1() {
        return alternative1;
    }

    public String getAlternative2() {
        return alternative2;
    }

    public String getAlternative3() {
        return alternative3;
    }

    public String getAlternative4() {
        return alternative4;
    }

    public double getW1() {
        return w1;
    }

    public double getW2() {
        return w2;
    }

    public double getW3() {
        return w3;
    }

    public double getW4() {
        return w4;
    }

    public int getN() {
        return n;
    }

    public Analysis(String alternative1, String alternative2, String alternative3, String alternative4, double w1, double w2, double w3, double w4){
        this.alternative1 = alternative1;
        this.alternative2 = alternative2;
        this.alternative3 = alternative3;
        this.alternative4 = alternative4;
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
    }
}