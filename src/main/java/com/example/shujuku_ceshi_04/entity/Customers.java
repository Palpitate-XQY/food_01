package com.example.shujuku_ceshi_04.entity;

import java.util.List;

public class Customers {
    private int customerID;
    private String customerName;
    private String customerPhone;
    private Integer tableID;
    private String status;
    private Integer queueNumber;
    private Integer orderID;

    // 订单对象，用于表示和订单的关联关系（非JPA关联，只是简单的对象引用）
    private Orders order;
    // 顾客评价列表，用于表示和顾客评价的关联关系（非JPA关联，只是简单的集合存储）
    private List<Reviews> reviews;

    // 以下是各属性的getter和setter方法
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getTableID() {
        return tableID;
    }

    public void setTableID(Integer tableID) {
        this.tableID = tableID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }
}