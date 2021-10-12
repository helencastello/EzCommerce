package com.example.ezcommerce.Model;

import java.util.List;

public class Data {
    int statusCode;
    String nim;
    String nama;
    int productId;
    String credits;
    List<Product> products;

    public Data(int statusCode, String nim, String nama, int productId, String credits, List<Product> products) {
        this.statusCode = statusCode;
        this.nim = nim;
        this.nama = nama;
        this.productId = productId;
        this.credits = credits;
        this.products = products;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
