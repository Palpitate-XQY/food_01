package com.example.shujuku_ceshi_04.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.shujuku_ceshi_04.entity.Customers;
import java.util.List;

@Repository
public class CustomersRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customers saveCustomer(Customers customer) {
        String sql = "INSERT INTO Customers (CustomerName, CustomerPhone) VALUES (?,?)";
        jdbcTemplate.update(sql, customer.getCustomerName(), customer.getCustomerPhone());
        return customer;
    }

    public List<Customers> findAllCustomers() {
        String sql = "SELECT * FROM Customers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customers customer = new Customers();
            customer.setCustomerID(rs.getInt("CustomerID"));
            customer.setCustomerName(rs.getString("CustomerName"));
            customer.setCustomerPhone(rs.getString("CustomerPhone"));
            customer.setTableID(rs.getInt("TableID"));
            customer.setStatus(rs.getString("Status"));
            customer.setQueueNumber(rs.getInt("QueueNumber"));
            customer.setOrderID(rs.getInt("OrderID"));
            return customer;
        });
    }

    // 根据顾客 ID 查找顾客信息
    public Customers findCustomerById(int customerId) {
        String sql = "SELECT * FROM Customers WHERE CustomerID =?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Customers customer = new Customers();
            customer.setCustomerID(rs.getInt("CustomerID"));
            customer.setCustomerName(rs.getString("CustomerName"));
            customer.setCustomerPhone(rs.getString("CustomerPhone"));
            customer.setTableID(rs.getInt("TableID"));
            customer.setStatus(rs.getString("Status"));
            customer.setQueueNumber(rs.getInt("QueueNumber"));
            customer.setOrderID(rs.getInt("OrderID"));
            return customer;
        }, customerId);
    }
}