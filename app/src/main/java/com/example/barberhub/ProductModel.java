package com.example.barberhub;

public class ProductModel {

    private String name;
    private double price;
    private int quantity;
    private String imageUrl;  // This will be the base64 string

    public ProductModel(String name, double price, int quantity, String imageUrl) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
