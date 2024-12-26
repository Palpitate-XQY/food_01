package com.example.shujuku_ceshi_04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shujuku_ceshi_04.entity.Customers;
import com.example.shujuku_ceshi_04.repository.CustomersRepository;
import java.util.List;

@Service
public class CustomersService {
    private final CustomersRepository customersRepository;

    @Autowired
    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Transactional
    public Customers saveCustomer(Customers customer) {
        return customersRepository.saveCustomer(customer);
    }

    public List<Customers> findAllCustomers() {
        return customersRepository.findAllCustomers();
    }

    // 根据顾客 ID 查找顾客信息
    public Customers findCustomerById(int customerId) {
        return customersRepository.findCustomerById(customerId);
    }

    // 分配桌位（根据业务需求完善逻辑）
    public String assignTable(int customerId) {
        // 此处可添加分配桌位的业务逻辑，如调用 TablesService 或直接调用 TablesRepository 进行操作
        return "桌位分配成功";
    }

    // 点餐（根据业务需求完善逻辑）
    public String placeOrder(int customerId, List<Integer> dishIds, List<Integer> quantities) {
        // 此处可添加点餐的业务逻辑，如调用 OrdersService 或 MenusService 进行操作
        return "点餐成功";
    }

    // 结束用餐（根据业务需求完善逻辑）
    public String endMeal(int customerId) {
        // 此处可添加结束用餐的业务逻辑，如更新顾客状态、释放桌位等操作
        return "结束用餐成功";
    }
}