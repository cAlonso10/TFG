package com.example.tfg;

import java.util.List;

public class Pedido {
    private int orderId;
    private String status;
    private List<CartItem> items;
    private double totalPrice;

    public Pedido(String orderId, String status, List<CartItem> items, double totalPrice) {
        this.orderId = Integer.parseInt(orderId);
        this.status = status;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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