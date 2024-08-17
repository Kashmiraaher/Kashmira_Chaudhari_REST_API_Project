package com.trialstudio.customstudio.model;

public class OrderProduct {
    private Product product;  // Product details
    private int quantity;     // Quantity of the product in the order

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
