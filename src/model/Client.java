package model;

public class Client {
    private int id;
    private String name;
    private int idAddress;
    private String email;
    private int age;

    public Client() {}
    public Client(int id, String name, int address, String email, int age) {
        this.id = id;
        this.name = name;
        this.idAddress = address;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int address) {
        this.idAddress = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString()
    {
        String client = "";
        client += id + name + idAddress + email + age;
        return client;
    }
}
