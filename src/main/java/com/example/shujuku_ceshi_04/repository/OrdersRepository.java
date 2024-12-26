package com.example.shujuku_ceshi_04.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.shujuku_ceshi_04.entity.Orders;
import java.util.List;

@Repository
public class OrdersRepository {
    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrdersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Orders saveOrder(Orders order) {
        String sql = "INSERT INTO Orders (CustomerID, OrderTime, TotalPrice, IsPrintedReceipt) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, order.getCustomerID(), order.getOrderTime(), order.getTotalPrice(), order.isPrintedReceipt());
        return order;
    }

    public List<Orders> findAllOrders() {
        String sql = "SELECT * FROM Orders";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Orders order = new Orders();
            order.setOrderID(rs.getInt("OrderID"));
            order.setCustomerID(rs.getInt("CustomerID"));
            order.setOrderTime(rs.getTimestamp("OrderTime"));
            order.setTotalPrice(rs.getDouble("TotalPrice"));
            order.setPrintedReceipt(rs.getBoolean("IsPrintedReceipt"));
            return order;
        });
    }

    // 其他与订单相关的数据库操作，如根据 ID 查询订单、更新订单信息等
}