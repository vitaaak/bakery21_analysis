package sample.entity;

public class AnalysisForControl {
    private String alternative1;
    private String alternative2;
    private String alternative3;
    private String alternative4;

    private int scale;

    private double w1;
    private double w2;
    private double w3;
    private double w4;

    private int N;

public AnalysisForControl(){};



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

    public int getN(){ return N;}

    public void setScale(int scaleInt) {
        this.scale = scaleInt;
    }

    public void setN(int n) {
        N = n;
    }

    public void setW1(double w1) {
        this.w1 = w1;
    }

    public void setW2(double w2) {
        this.w2 = w2;
    }

    public void setW3(double w3) {
        this.w3 = w3;
    }

    public void setW4(double w4) {
        this.w4 = w4;
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
}
