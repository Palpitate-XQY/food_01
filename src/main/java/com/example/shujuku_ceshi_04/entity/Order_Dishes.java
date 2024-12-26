package com.example.shujuku_ceshi_04.entity;

public class Order_Dishes {
    private int orderDishID;
    private int orderID;
    private int menuID;
    private int quantity;

    // 关联的订单对象（非JPA关联，只是简单的对象引用）
    private Orders order;
    // 关联的菜单对象（非JPA关联，只是简单的对象引用）
    private Menus menu;

    // 以下是各属性的getter和setter方法
    public int getOrderDishID() {
        return orderDishID;
    }

    public void setOrderDishID(int orderDishID) {
        this.orderDishID = orderDishID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Menus getMenu() {
        return menu;
    }

    public void setMenu(Menus menu) {
        this.menu = menu;
    }
}