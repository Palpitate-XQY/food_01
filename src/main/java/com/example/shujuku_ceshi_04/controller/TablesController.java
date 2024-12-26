package com.example.shujuku_ceshi_04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shujuku_ceshi_04.entity.Tables;
import com.example.shujuku_ceshi_04.service.TablesService;
import java.util.List;

@RestController
@RequestMapping("/tables")
public class TablesController {
    private final TablesService tablesService;

    @Autowired
    public TablesController(TablesService tablesService) {
        this.tablesService = tablesService;
    }

    // 保存桌位信息接口
    @PostMapping("/save")
    public ResponseEntity<Tables> saveTable(@RequestBody Tables table) {
        Tables savedTable = tablesService.saveTable(table);
        return new ResponseEntity<>(savedTable, HttpStatus.CREATED);
    }

    // 查询所有桌位信息接口
    @GetMapping("/all")
    public ResponseEntity<List<Tables>> getAllTables() {
        List<Tables> tables = tablesService.findAllTables();
        return new ResponseEntity<>(tables, HttpStatus.OK);
    }

    // 根据桌位 ID 查询桌位信息接口
    @GetMapping("/{tableId}")
    public ResponseEntity<Tables> getTableById(@PathVariable int tableId) {
        Tables table = tablesService.findTableById(tableId);
        if (table!= null) {
            return new ResponseEntity<>(table, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 释放桌位接口（例如用餐结束后释放）
    @GetMapping("/{tableId}/release")
    public ResponseEntity<String> releaseTable(@PathVariable int tableId) {
        String result = tablesService.releaseTable(tableId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}