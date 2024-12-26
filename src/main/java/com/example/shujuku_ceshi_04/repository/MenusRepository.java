package com.example.shujuku_ceshi_04.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.shujuku_ceshi_04.entity.Menus;
import java.util.List;

@Repository
public class MenusRepository {
    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public MenusRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Menus saveMenu(Menus menu) {
        String sql = "INSERT INTO Menus (DishName, Price, Stock, IsAvailable) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, menu.getDishName(), menu.getPrice(), menu.getStock(), menu.isAvailable());
        return menu;
    }

    public List<Menus> findAllMenus() {
        String sql = "SELECT * FROM Menus";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Menus menu = new Menus();
            menu.setMenuID(rs.getInt("MenuID"));
            menu.setDishName(rs.getString("DishName"));
            menu.setPrice(rs.getDouble("Price"));
            menu.setStock(rs.getInt("Stock"));
            menu.setAvailable(rs.getBoolean("IsAvailable"));
            return menu;
        });
    }

    // 封装查询菜单的方法
    public Menus findMenuById(int menuId) {
        String sql = "SELECT * FROM Menus WHERE MenuID =?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Menus menu = new Menus();
            menu.setMenuID(rs.getInt("MenuID"));
            menu.setDishName(rs.getString("DishName"));
            menu.setPrice(rs.getDouble("Price"));
            menu.setStock(rs.getInt("Stock"));
            menu.setAvailable(rs.getBoolean("IsAvailable"));
            return menu;
        }, menuId);
    }

    // 封装更新库存的方法
    public void updateStock(int menuId, int quantity) {
        String sql = "UPDATE Menus SET Stock = Stock -? WHERE MenuID =?";
        jdbcTemplate.update(sql, quantity, menuId);
    }

    // 封装检查菜品是否可用的方法
    public boolean isMenuItemAvailable(int menuId) {
        Menus menu = findMenuById(menuId);
        return menu.isAvailable() && menu.getStock() > 0;
    }

    // 其他与菜单相关的数据库操作，如根据 ID 查询菜单、更新菜单信息等
}