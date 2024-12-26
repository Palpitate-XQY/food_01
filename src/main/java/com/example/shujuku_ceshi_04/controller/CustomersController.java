package com.example.shujuku_ceshi_04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shujuku_ceshi_04.entity.Customers;
import com.example.shujuku_ceshi_04.service.CustomersService;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    private final CustomersService customersService;

    @Autowired
    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    // 保存顾客信息接口
    @PostMapping("/save")
    public ResponseEntity<Customers> saveCustomer(@RequestBody Customers customer) {
        Customers savedCustomer = customersService.saveCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    // 查询所有顾客信息接口
    @GetMapping("/all")
    public ResponseEntity<List<Customers>> getAllCustomers() {
        List<Customers> customers = customersService.findAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // 根据顾客 ID 查询顾客信息接口
    @GetMapping("/{customerId}")
    public ResponseEntity<Customers> getCustomerById(@PathVariable int customerId) {
        Customers customer = customersService.findCustomerById(customerId);
        if (customer!= null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 分配桌位接口
    @GetMapping("/{customerId}/assignTable")
    public ResponseEntity<String> assignTable(@PathVariable int customerId) {
        String result = customersService.assignTable(customerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 点餐接口
    @PostMapping("/{customerId}/placeOrder")
    public ResponseEntity<String> placeOrder(@PathVariable int customerId, @RequestBody OrderRequest orderRequest) {
        String result = customersService.placeOrder(customerId, orderRequest.getDishIds(), orderRequest.getQuantities());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 结束用餐接口
    @GetMapping("/{customerId}/endMeal")
    public ResponseEntity<String> endMeal(@PathVariable int customerId) {
        String result = customersService.endMeal(customerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 内部类用于接收点餐请求的参数
    private static class OrderRequest {
        private List<Integer> dishIds;
        private List<Integer> quantities;

        public List<Integer> getDishIds() {
            return dishIds;
        }

        public void setDishIds(List<Integer> dishIds) {
            this.dishIds = dishIds;
        }

        public List<Integer> getQuantities() {
            return quantities;
        }

        public void setQuantities(List<Integer> quantities) {
            this.quantities = quantities;
        }
    }
}