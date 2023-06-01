package com.example.tfg;

import java.util.List;

public class Pedido {

    private String email;
    private String status;
    private List<CartItem> items;
    private double totalPrice;

    public Pedido(String email, String status, List<CartItem> items, double totalPrice) {
        this.email = email;
        this.status = status;
        this.items = items;
        this.totalPrice = totalPrice;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
