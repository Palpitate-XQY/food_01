package com.example.shujuku_ceshi_04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shujuku_ceshi_04.entity.Menus;
import com.example.shujuku_ceshi_04.repository.MenusRepository;
import java.util.List;

@Service
public class MenusService {
    private final MenusRepository menusRepository;

    @Autowired
    public MenusService(MenusRepository menusRepository) {
        this.menusRepository = menusRepository;
    }

    // 保存菜品信息到数据库
    @Transactional
    public Menus saveMenu(Menus menu) {
        return menusRepository.saveMenu(menu);
    }

    // 查询所有菜品信息
    public List<Menus> findAllMenus() {
        return menusRepository.findAllMenus();
    }

    // 根据菜品 ID 查询菜品信息
    public Menus findMenuById(int menuId) {
        String sql = "SELECT * FROM Menus WHERE MenuID =?";
        return menusRepository.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Menus menu = new Menus();
            menu.setMenuID(rs.getInt("MenuID"));
            menu.setDishName(rs.getString("DishName"));
            menu.setPrice(rs.getDouble("Price"));
            menu.setStock(rs.getInt("Stock"));
            menu.setAvailable(rs.getBoolean("IsAvailable"));
            return menu;
        }, menuId);
    }

    // 更新菜品库存（例如点餐时调用此方法减少库存）
    @Transactional
    public void updateStock(int menuId, int quantity) {
        String sql = "UPDATE Menus SET Stock = Stock -? WHERE MenuID =?";
        menusRepository.jdbcTemplate.update(sql, quantity, menuId);
    }

    // 检查菜品是否可点（基于库存是否足够以及菜品是否处于上架状态等条件判断）
    public boolean isMenuItemAvailable(int menuId) {
        Menus menu = findMenuById(menuId);
        return menu.isAvailable() && menu.getStock() > 0;
    }

    // 添加新菜品（可根据实际需求补充更多逻辑，比如验证菜品信息合法性等）
    @Transactional
    public Menus addNewMenuItem(String dishName, double price, int stock, boolean isAvailable) {
        Menus menu = new Menus();
        menu.setDishName(dishName);
        menu.setPrice(price);
        menu.setStock(stock);
        menu.setAvailable(isAvailable);
        return saveMenu(menu);
    }

    // 下架菜品（将菜品的可用状态设为 false）
    @Transactional
    public void removeMenuItem(int menuId) {
        Menus menu = findMenuById(menuId);
        menu.setAvailable(false);
        updateMenu(menu);
    }

    // 更新菜品的其他信息（除库存外的其他字段更新，比如价格、菜品名称等）
    @Transactional
    public void updateMenu(Menus menu) {
        String sql = "UPDATE Menus SET DishName =?, Price =?, IsAvailable =? WHERE MenuID =?";
        menusRepository.jdbcTemplate.update(sql, menu.getDishName(), menu.getPrice(), menu.isAvailable(), menu.getMenuID());
    }

    // 根据菜品名称模糊查询菜品列表（可用于搜索功能）
    public List<Menus> findMenusByDishNameLike(String dishName) {
        String sql = "SELECT * FROM Menus WHERE DishName LIKE?";
        return menusRepository.jdbcTemplate.query(sql, (rs, rowNum) -> {
            Menus menu = new Menus();
            menu.setMenuID(rs.getInt("MenuID"));
            menu.setDishName(rs.getString("DishName"));
            menu.setPrice(rs.getDouble("Price"));
            menu.setStock(rs.getInt("Stock"));
            menu.setAvailable(rs.getBoolean("IsAvailable"));
            return menu;
        }, "%" + dishName + "%");
    }

    // 其他与菜单相关的业务逻辑，比如获取热门菜品（根据点餐次数等统计逻辑，这里只是示例方法名，需进一步完善实现逻辑）
    public List<Menus> getPopularMenuItems() {
        // 此处可编写 SQL 查询语句结合数据库统计功能（如 COUNT 等）来获取热门菜品信息，例如查询被点次数较多的菜品
        // 然后使用 jdbcTemplate 查询并返回相应的 Menus 列表
        return null;
    }
}