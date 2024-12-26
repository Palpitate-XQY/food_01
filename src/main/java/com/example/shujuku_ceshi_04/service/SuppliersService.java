package com.example.shujuku_ceshi_04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shujuku_ceshi_04.entity.Suppliers;
import com.example.shujuku_ceshi_04.repository.SuppliersRepository;
import java.util.List;

@Service
public class SuppliersService {
    private final SuppliersRepository suppliersRepository;

    @Autowired
    public SuppliersService(SuppliersRepository suppliersRepository) {
        this.suppliersRepository = suppliersRepository;
    }

    @Transactional
    public Suppliers saveSupplier(Suppliers supplier) {
        return suppliersRepository.saveSupplier(supplier);
    }

    public List<Suppliers> findAllSuppliers() {
        return suppliersRepository.findAllSuppliers();
    }

    // 根据供应商 ID 查询供应商信息
    public Suppliers findSupplierById(int supplierId) {
        String sql = "SELECT * FROM Suppliers WHERE SupplierID =?";
        return suppliersRepository.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Suppliers supplier = new Suppliers();
            supplier.setSupplierID(rs.getInt("SupplierID"));
            supplier.setSupplierName(rs.getString("SupplierName"));
            supplier.setContactPerson(rs.getString("ContactPerson"));
            supplier.setContactPhone(rs.getString("ContactPhone"));
            supplier.setAddress(rs.getString("Address"));
            return supplier;
        }, supplierId);
    }

    // 更新供应商信息
    @Transactional
    public void updateSupplier(Suppliers supplier) {
        String sql = "UPDATE Suppliers SET SupplierName =?, ContactPerson =?, ContactPhone =?, Address =? WHERE SupplierID =?";
        suppliersRepository.jdbcTemplate.update(sql, supplier.getSupplierName(), supplier.getContactPerson(), supplier.getContactPhone(), supplier.getAddress(), supplier.getSupplierID());
    }

    // 其他与供应商相关的业务逻辑，如添加供应商、删除供应商等
}