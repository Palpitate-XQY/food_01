package com.example.shujuku_ceshi_04.entity;

import java.util.List;

public class Menus {
    private int menuID;
    private String dishName;
    private double price;
    private int stock;
    private boolean isAvailable;

    // 用于存储该菜品在各个订单中的关联信息（非JPA关联，只是简单的集合存储）
    private List<Order_Dishes> orderDishes;

    // 以下是各属性的getter和setter方法
    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<Order_Dishes> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<Order_Dishes> orderDishes) {
        this.orderDishes = orderDishes;
    }
}