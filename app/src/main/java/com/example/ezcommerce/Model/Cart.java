package com.example.ezcommerce.Model;

public class Cart {
    int productId;
    int qty;

    public Cart(int productId, int qty) {
        this.productId = productId;
        this.qty = qty;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
