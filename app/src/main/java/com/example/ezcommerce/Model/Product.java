package com.example.ezcommerce.Model;

import android.graphics.Bitmap;

import com.example.ezcommerce.service.ProductService;

import java.util.concurrent.ExecutionException;

public class Product {
    int id;
    String name;
    String description;
    float price;
    String author;
    String type;
    String img;
    Bitmap image;
    boolean inCart;
    String category;

    public Product(int id, String name, String description, float price, String author, String type, String img, boolean inCart, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.type = type;
        this.img = img;
        this.inCart = inCart;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        ProductService.DownloadImage downloadImage = new ProductService.DownloadImage();
        try {
            this.image = downloadImage.execute(getImg()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImage() {
        return image;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
