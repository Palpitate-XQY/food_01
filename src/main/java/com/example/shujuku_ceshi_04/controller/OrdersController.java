package com.example.shujuku_ceshi_04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shujuku_ceshi_04.entity.Orders;
import com.example.shujuku_ceshi_04.service.OrdersService;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    // 保存订单信息接口
    @PostMapping("/save")
    public ResponseEntity<Orders> saveOrder(@RequestBody Orders order) {
        Orders savedOrder = ordersService.saveOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    // 查询所有订单信息接口
    @GetMapping("/all")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.findAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 根据订单 ID 查询订单信息接口
    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable int orderId) {
        Orders order = ordersService.findOrderById(orderId);
        if (order!= null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 创建订单接口（点餐时调用，包含菜品关联等操作）
    @PostMapping("/create")
    public ResponseEntity<Integer> createOrder(@RequestBody OrderCreateRequest request) {
        int orderId = ordersService.createOrder(request.getCustomerId(), request.getMenuIds(), request.getQuantities());
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    // 更新订单状态接口（如已付款、已完成等）
    @GetMapping("/{orderId}/updateStatus")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId, @RequestParam String status) {
        ordersService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>("订单状态更新成功", HttpStatus.OK);
    }

    // 根据客户 ID 查询订单历史接口
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable int customerId) {
        List<Orders> orders = ordersService.findOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 内部类用于接收创建订单请求的参数
    private static class OrderCreateRequest {
        private int customerId;
        private List<Integer> menuIds;
        private List<Integer> quantities;

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public List<Integer> getMenuIds() {
            return menuIds;
        }

        public void setMenuIds(List<Integer> menuIds) {
            this.menuIds = menuIds;
        }

        public List<Integer> getQuantities() {
            return quantities;
        }

        public void setQuantities(List<Integer> quantities) {
            this.quantities = quantities;
        }
    }
}