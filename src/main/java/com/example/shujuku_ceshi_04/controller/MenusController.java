package com.example.shujuku_ceshi_04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shujuku_ceshi_04.entity.Menus;
import com.example.shujuku_ceshi_04.service.MenusService;
import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenusController {
    private final MenusService menusService;

    @Autowired
    public MenusController(MenusService menusService) {
        this.menusService = menusService;
    }

    // 保存菜品信息接口
    @PostMapping("/save")
    public ResponseEntity<Menus> saveMenu(@RequestBody Menus menu) {
        Menus savedMenu = menusService.saveMenu(menu);
        return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
    }

    // 查询所有菜品信息接口
    @GetMapping("/all")
    public ResponseEntity<List<Menus>> getAllMenus() {
        List<Menus> menus = menusService.findAllMenus();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    // 根据菜品 ID 查询菜品信息接口
    @GetMapping("/{menuId}")
    public ResponseEntity<Menus> getMenuById(@PathVariable int menuId) {
        Menus menu = menusService.findMenuById(menuId);
        if (menu!= null) {
            return new ResponseEntity<>(menu, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 检查菜品是否可点接口（根据库存、是否上架等条件判断）
    @GetMapping("/{menuId}/available")
    public ResponseEntity<Boolean> isMenuItemAvailable(@PathVariable int menuId) {
        boolean available = menusService.isMenuItemAvailable(menuId);
        return new ResponseEntity<>(available, HttpStatus.OK);
    }

    // 根据菜品名称模糊查询菜品接口
    @GetMapping("/search")
    public ResponseEntity<List<Menus>> searchMenusByDishName(@RequestParam String dishName) {
        List<Menus> menus = menusService.findMenusByDishNameLike(dishName);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}