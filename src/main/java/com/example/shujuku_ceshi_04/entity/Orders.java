package com.example.shujuku_ceshi_04.entity;

import java.util.Date;

public class Orders {
    private int orderID;
    private int customerID;
    private Date orderTime;
    private double totalPrice;
    private boolean isPrintedReceipt;
    private String status; // 新增订单状态字段

    // Getter 和 Setter 方法
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPrintedReceipt() {
        return isPrintedReceipt;
    }

    public void setPrintedReceipt(boolean printedReceipt) {
        isPrintedReceipt = printedReceipt;
    }

    public String getStatus() { // 新增获取订单状态的方法
        return status;
    }

    public void setStatus(String status) { // 新增设置订单状态的方法
        this.status = status;
    }
}