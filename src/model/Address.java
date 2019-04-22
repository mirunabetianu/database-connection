package model;

public class Address {
    private int id;
    private String street;
    private String city;
    private String country;
    private String zip_code;

    public Address(){}
    public Address(int id, String street, String city, String country, String zip_code) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.country = country;
        this.zip_code = zip_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
