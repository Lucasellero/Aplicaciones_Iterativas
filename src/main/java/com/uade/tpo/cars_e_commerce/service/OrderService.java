package com.uade.tpo.cars_e_commerce.service;

import java.util.List;

import com.uade.tpo.cars_e_commerce.entity.Order;

public interface OrderService {
    List<Order> getOrders();
    List<Order> getOrdersByUserId(Long userId);
    Order getOrderById(long orderId);
    Order save(Order order);
}