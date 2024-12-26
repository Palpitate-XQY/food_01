package com.example.shujuku_ceshi_04.service;

import com.example.shujuku_ceshi_04.entity.Menus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shujuku_ceshi_04.entity.Order_Dishes;
import com.example.shujuku_ceshi_04.entity.Orders;
import com.example.shujuku_ceshi_04.repository.MenusRepository;
import com.example.shujuku_ceshi_04.repository.Order_DishesRepository;
import com.example.shujuku_ceshi_04.repository.OrdersRepository;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final Order_DishesRepository orderDishesRepository;
    private final MenusRepository menusRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, Order_DishesRepository orderDishesRepository, MenusRepository menusRepository) {
        this.ordersRepository = ordersRepository;
        this.orderDishesRepository = orderDishesRepository;
        this.menusRepository = menusRepository;
    }

    @Transactional
    public Orders saveOrder(Orders order) {
        return ordersRepository.saveOrder(order);
    }

    public List<Orders> findAllOrders() {
        return ordersRepository.findAllOrders();
    }

    // 根据订单 ID 查询订单信息
    public Orders findOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE OrderID =?";
        return ordersRepository.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Orders resultOrder = new Orders();
            resultOrder.setOrderID(rs.getInt("OrderID"));
            resultOrder.setCustomerID(rs.getInt("CustomerID"));
            resultOrder.setOrderTime(rs.getTimestamp("OrderTime"));
            resultOrder.setTotalPrice(rs.getDouble("TotalPrice"));
            resultOrder.setPrintedReceipt(rs.getBoolean("IsPrintedReceipt"));
            resultOrder.setStatus(rs.getString("Status")); // 获取订单状态
            return resultOrder;
        }, orderId);
    }

    // 更新订单状态（如已付款、已完成等）
    @Transactional
    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE Orders SET Status =? WHERE OrderID =?";
        ordersRepository.jdbcTemplate.update(sql, status, orderId);
    }

    // 创建订单并关联菜品（点餐流程中调用）
    @Transactional
    public int createOrder(int customerId, List<Integer> menuIds, List<Integer> quantities) {
        // 创建订单
        Orders order = new Orders();
        order.setCustomerID(customerId);
        order.setOrderTime(new Date());
        order.setTotalPrice(0.0);
        Orders savedOrder = saveOrder(order);

        // 关联菜品并计算总价
        double totalPrice = 0.0;
        for (int i = 0; i < menuIds.size(); i++) {
            int menuId = menuIds.get(i);
            int quantity = quantities.get(i);
            Menus menu = menusRepository.findMenuById(menuId);
            totalPrice += menu.getPrice() * quantity;

            // 保存订单菜品关联信息
            Order_Dishes orderDish = new Order_Dishes();
            orderDish.setOrderID(savedOrder.getOrderID());
            orderDish.setMenuID(menuId);
            orderDish.setQuantity(quantity);
            orderDishesRepository.saveOrderDish(orderDish);

            // 更新菜品库存
            menusRepository.updateStock(menuId, quantity);
        }

        // 更新订单总价
        updateOrderTotalPrice(savedOrder.getOrderID(), totalPrice);

        return savedOrder.getOrderID();
    }

    // 更新订单总价
    @Transactional
    public void updateOrderTotalPrice(int orderId, double totalPrice) {
        String sql = "UPDATE Orders SET TotalPrice =? WHERE OrderID =?";
        ordersRepository.jdbcTemplate.update(sql, totalPrice, orderId);
    }

    // 其他与订单相关的业务逻辑，如查询用户订单历史、统计订单金额等
    public List<Orders> findOrdersByCustomerId(int customerId) {
        String sql = "SELECT * FROM Orders WHERE CustomerID =?";
        return ordersRepository.jdbcTemplate.query(sql, (rs, rowNum) -> {
            Orders order = new Orders();
            order.setOrderID(rs.getInt("OrderID"));
            order.setCustomerID(rs.getInt("CustomerID"));
            order.setOrderTime(rs.getTimestamp("OrderTime"));
            order.setTotalPrice(rs.getDouble("TotalPrice"));
            order.setPrintedReceipt(rs.getBoolean("IsPrintedReceipt"));
            order.setStatus(rs.getString("Status")); // 获取订单状态
            return order;
        }, customerId);
    }
}