package com.example.shujuku_ceshi_04.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.shujuku_ceshi_04.entity.Tables;
import java.util.List;

@Repository
public class TablesRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TablesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Tables saveTable(Tables table) {
        String sql = "INSERT INTO Tables (IsAvailable, TableCapacity) VALUES (?,?)";
        jdbcTemplate.update(sql, table.isAvailable(), table.getTableCapacity());
        return table;
    }

    public List<Tables> findAllTables() {
        String sql = "SELECT * FROM Tables";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tables table = new Tables();
            table.setTableID(rs.getInt("TableID"));
            table.setAvailable(rs.getBoolean("IsAvailable"));
            table.setTableCapacity(rs.getInt("TableCapacity"));
            return table;
        });
    }

    // 根据表的 ID 查找表信息
    public Tables findTableById(int tableId) {
        String sql = "SELECT * FROM Tables WHERE TableID =?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Tables table = new Tables();
            table.setTableID(rs.getInt("TableID"));
            table.setAvailable(rs.getBoolean("IsAvailable"));
            table.setTableCapacity(rs.getInt("TableCapacity"));
            return table;
        }, tableId);
    }

    // 更新表的可用性（例如释放桌位时调用）
    public void updateTableAvailability(int tableId, boolean isAvailable) {
        String sql = "UPDATE Tables SET IsAvailable =? WHERE TableID =?";
        jdbcTemplate.update(sql, isAvailable, tableId);
    }
}