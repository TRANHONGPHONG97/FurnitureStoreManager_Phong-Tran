package com.cg.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Product {
    private int id;
    private String name;
    private String image;
    private double price;
    private int quantity;

    private int category_id;

    public Product(String name, String image, String price, String quantity, String category_id) {
        this.name = name;
        this.image = image;
        this.price = Double.parseDouble(price);
        this.quantity = Integer.parseInt(quantity);
        this.category_id = Integer.parseInt(category_id);
    }

    public Product(String name, String image, double price, int quantity, String category_id) {
        this.name = name;
        this.image = image;
        this.price = Double.parseDouble(String.valueOf(price));
        this.quantity = Integer.parseInt(String.valueOf(quantity));
        this.category_id = Integer.parseInt(category_id);
    }

    public Product(String name, String image, double price, String quantity, String category_id) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = Integer.parseInt(quantity);
        this.category_id = Integer.parseInt(category_id);
    }

    public Product(int id, String name, String image, String price, String quantity, int category_id) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = Double.parseDouble(price);
        this.quantity = Integer.parseInt(quantity);
        this.category_id = category_id;
    }

    public static String displayVND(BigDecimal price) {
        String patternVND = ",###₫";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(price);
    }

    public Product() {
    }

    public Product(int id, String name, String image, double price, int quantity, int category_id) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.category_id = category_id;
    }

    public Product(String name, String image, double price, int quantity, int category_id) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.category_id = category_id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category_id=" + category_id +
                '}';
    }
}
