package com.uade.tpo.cars_e_commerce.service;

import java.util.List;

import com.uade.tpo.cars_e_commerce.entity.Order;
import com.uade.tpo.cars_e_commerce.entity.dto.OrderDTO;

public interface OrderService {
    List<OrderDTO> getOrders();
    List<OrderDTO> getOrdersByUserId(Long userId);
    OrderDTO getOrderById(long orderId);
    Order save(Order order);

}