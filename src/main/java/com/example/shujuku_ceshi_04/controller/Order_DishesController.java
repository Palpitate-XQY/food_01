package com.example.shujuku_ceshi_04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shujuku_ceshi_04.entity.Order_Dishes;
import com.example.shujuku_ceshi_04.service.Order_DishesService;
import java.util.List;

@RestController
@RequestMapping("/order-dishes")
public class Order_DishesController {
    private final Order_DishesService orderDishesService;

    @Autowired
    public Order_DishesController(Order_DishesService orderDishesService) {
        this.orderDishesService = orderDishesService;
    }

    // 保存订单菜品关联信息接口
    @PostMapping("/save")
    public ResponseEntity<Order_Dishes> saveOrderDish(@RequestBody Order_Dishes orderDish) {
        Order_Dishes savedOrderDish = orderDishesService.saveOrderDish(orderDish);
        return new ResponseEntity<>(savedOrderDish, HttpStatus.CREATED);
    }

    // 查询所有订单菜品关联信息接口
    @GetMapping("/all")
    public ResponseEntity<List<Order_Dishes>> getAllOrderDishes() {
        List<Order_Dishes> orderDishes = orderDishesService.findAllOrderDishes();
        return new ResponseEntity<>(orderDishes, HttpStatus.OK);
    }

    // 根据订单 ID 查询订单菜品关联信息接口
    @GetMapping("/{orderId}")
    public ResponseEntity<List<Order_Dishes>> getOrderDishesByOrderId(@PathVariable int orderId) {
        List<Order_Dishes> orderDishes = orderDishesService.findOrderDishesByOrderId(orderId);
        return new ResponseEntity<>(orderDishes, HttpStatus.OK);
    }
}