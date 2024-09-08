package com.uade.tpo.cars_e_commerce.service;

import java.util.List;

import com.uade.tpo.cars_e_commerce.entity.Order;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;

public interface OrderService {
    Order checkoutCarrito(Long cartId) throws ResourceNotFoundException;
    List<Order> getOrders();
    List<Order> getOrdersByUserId(Long userId);
}