package entity;

public class Addresses {
    private String cityName;
    private String address;
    private int telephone;

    public Addresses(String cityName, String address, int telephone){
        this.cityName = cityName;
        this.address = address;
        this.telephone = telephone;
    }

    public Addresses(){ }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }
}
