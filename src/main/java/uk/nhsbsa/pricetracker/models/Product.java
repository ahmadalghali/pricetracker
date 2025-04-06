package uk.nhsbsa.pricetracker.models;

public class Product {
    private String url;
    private Double price;
    private String name;


    public Product(String url, Double price, String name) {
        this.url = url;
        this.price = price;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
