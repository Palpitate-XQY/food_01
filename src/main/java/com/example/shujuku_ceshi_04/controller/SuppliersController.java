package com.example.shujuku_ceshi_04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shujuku_ceshi_04.entity.Suppliers;
import com.example.shujuku_ceshi_04.service.SuppliersService;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SuppliersController {
    private final SuppliersService suppliersService;

    @Autowired
    public SuppliersController(SuppliersService suppliersService) {
        this.suppliersService = suppliersService;
    }

    // 保存供应商信息接口
    @PostMapping("/save")
    public ResponseEntity<Suppliers> saveSupplier(@RequestBody Suppliers supplier) {
        Suppliers savedSupplier = suppliersService.saveSupplier(supplier);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    // 查询所有供应商信息接口
    @GetMapping("/all")
    public ResponseEntity<List<Suppliers>> getAllSuppliers() {
        List<Suppliers> suppliers = suppliersService.findAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    // 根据供应商 ID 查询供应商信息接口
    @GetMapping("/{supplierId}")
    public ResponseEntity<Suppliers> getSupplierById(@PathVariable int supplierId) {
        Suppliers supplier = suppliersService.findSupplierById(supplierId);
        if (supplier!= null) {
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 更新供应商信息接口
    @PostMapping("/{supplierId}/update")
    public ResponseEntity<String> updateSupplier(@PathVariable int supplierId, @RequestBody Suppliers supplier) {
        supplier.setSupplierID(supplierId);
        suppliersService.updateSupplier(supplier);
        return new ResponseEntity<>("供应商信息更新成功", HttpStatus.OK);
    }
}