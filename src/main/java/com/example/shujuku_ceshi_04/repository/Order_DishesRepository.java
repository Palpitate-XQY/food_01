package com.example.shujuku_ceshi_04.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.shujuku_ceshi_04.entity.Order_Dishes;
import java.util.List;

@Repository
public class Order_DishesRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Order_DishesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Order_Dishes saveOrderDish(Order_Dishes orderDish) {
        String sql = "INSERT INTO Order_Dishes (OrderID, MenuID, Quantity) VALUES (?,?,?)";
        jdbcTemplate.update(sql, orderDish.getOrderID(), orderDish.getMenuID(), orderDish.getQuantity());
        return orderDish;
    }

    public List<Order_Dishes> findAllOrderDishes() {
        String sql = "SELECT * FROM Order_Dishes";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Order_Dishes orderDish = new Order_Dishes();
            orderDish.setOrderDishID(rs.getInt("Order_DishID"));
            orderDish.setOrderID(rs.getInt("OrderID"));
            orderDish.setMenuID(rs.getInt("MenuID"));
            orderDish.setQuantity(rs.getInt("Quantity"));
            return orderDish;
        });
    }

    // 根据订单菜品关联 ID 查找订单菜品关联信息
    public Order_Dishes findOrderDishById(int orderDishId) {
        String sql = "SELECT * FROM Order_Dishes WHERE Order_DishID =?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Order_Dishes orderDish = new Order_Dishes();
            orderDish.setOrderDishID(rs.getInt("Order_DishID"));
            orderDish.setOrderID(rs.getInt("OrderID"));
            orderDish.setMenuID(rs.getInt("MenuID"));
            orderDish.setQuantity(rs.getInt("Quantity"));
            return orderDish;
        }, orderDishId);
    }

    // 根据订单 ID 查询订单菜品关联信息
    public List<Order_Dishes> findOrderDishesByOrderId(int orderId) {
        String sql = "SELECT * FROM Order_Dishes WHERE OrderID =?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Order_Dishes orderDish = new Order_Dishes();
            orderDish.setOrderDishID(rs.getInt("Order_DishID"));
            orderDish.setOrderID(rs.getInt("OrderID"));
            orderDish.setMenuID(rs.getInt("MenuID"));
            orderDish.setQuantity(rs.getInt("Quantity"));
            return orderDish;
        }, orderId);
    }
}