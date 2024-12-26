package com.example.shujuku_ceshi_04.entity;

public class Reviews {
    private int reviewID;
    private int customerID;
    private int menuID;
    private String reviewContent;
    private java.util.Date reviewTime;

    // 关联的顾客对象（非JPA关联，只是简单的对象引用）
    private Customers customer;
    // 关联的菜单对象（非JPA关联，只是简单的对象引用）
    private Menus menu;

    // 以下是各属性的getter和setter方法
    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public java.util.Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(java.util.Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Menus getMenu() {
        return menu;
    }

    public void setMenu(Menus menu) {
        this.menu = menu;
    }
}