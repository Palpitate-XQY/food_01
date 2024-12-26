package com.example.shujuku_ceshi_04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shujuku_ceshi_04.entity.Tables;
import com.example.shujuku_ceshi_04.repository.TablesRepository;
import java.util.List;

@Service
public class TablesService {
    private final TablesRepository tablesRepository;

    @Autowired
    public TablesService(TablesRepository tablesRepository) {
        this.tablesRepository = tablesRepository;
    }

    @Transactional
    public Tables saveTable(Tables table) {
        return tablesRepository.saveTable(table);
    }

    public List<Tables> findAllTables() {
        return tablesRepository.findAllTables();
    }

    // 根据表的 ID 查找表信息
    public Tables findTableById(int tableId) {
        return tablesRepository.findTableById(tableId);
    }

    // 释放桌位（可根据实际需求实现）
    public String releaseTable(int tableId) {
        // 此处可添加释放桌位的业务逻辑，例如更新表的状态为可用
        // 调用 TablesRepository 的更新方法
        tablesRepository.updateTableAvailability(tableId, true);
        return "桌位已释放";
    }
}