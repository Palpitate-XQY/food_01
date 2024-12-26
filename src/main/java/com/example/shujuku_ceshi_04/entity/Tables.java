package com.example.shujuku_ceshi_04.entity;

public class Tables {
    private int tableID;
    private boolean isAvailable;
    private int tableCapacity;

    // 以下是各属性的getter和setter方法
    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getTableCapacity() {
        return tableCapacity;
    }

    public void setTableCapacity(int tableCapacity) {
        this.tableCapacity = tableCapacity;
    }
}