package com.example.shujuku_ceshi_04.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.shujuku_ceshi_04.entity.Suppliers;
import java.util.List;

@Repository
public class SuppliersRepository {
    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public SuppliersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Suppliers saveSupplier(Suppliers supplier) {
        String sql = "INSERT INTO Suppliers (SupplierName, ContactPerson, ContactPhone, Address) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, supplier.getSupplierName(), supplier.getContactPerson(), supplier.getContactPhone(), supplier.getAddress());
        return supplier;
    }

    public List<Suppliers> findAllSuppliers() {
        String sql = "SELECT * FROM Suppliers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Suppliers supplier = new Suppliers();
            supplier.setSupplierID(rs.getInt("SupplierID"));
            supplier.setSupplierName(rs.getString("SupplierName"));
            supplier.setContactPerson(rs.getString("ContactPerson"));
            supplier.setContactPhone(rs.getString("ContactPhone"));
            supplier.setAddress(rs.getString("Address"));
            return supplier;
        });
    }

    // 其他与供应商相关的数据库操作，如根据 ID 查询供应商、更新供应商信息等
}