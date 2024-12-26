package com.example.shujuku_ceshi_04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shujuku_ceshi_04.entity.Order_Dishes;
import com.example.shujuku_ceshi_04.repository.Order_DishesRepository;
import java.util.List;

@Service
public class Order_DishesService {
    private final Order_DishesRepository orderDishesRepository;

    @Autowired
    public Order_DishesService(Order_DishesRepository orderDishesRepository) {
        this.orderDishesRepository = orderDishesRepository;
    }

    @Transactional
    public Order_Dishes saveOrderDish(Order_Dishes orderDish) {
        return orderDishesRepository.saveOrderDish(orderDish);
    }

    public List<Order_Dishes> findAllOrderDishes() {
        return orderDishesRepository.findAllOrderDishes();
    }

    // 根据订单菜品关联 ID 查找订单菜品关联信息
    public Order_Dishes findOrderDishById(int orderDishId) {
        return orderDishesRepository.findOrderDishById(orderDishId);
    }

    // 根据订单 ID 查询订单菜品关联信息
    public List<Order_Dishes> findOrderDishesByOrderId(int orderId) {
        return orderDishesRepository.findOrderDishesByOrderId(orderId);
    }
}