package model;

public class Orders {
    private int id;
    private int idClient;
    private int idProduct;
    private int price;


    public Orders(int id, int idClient, int idProduct, int price) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
