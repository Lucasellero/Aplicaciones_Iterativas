package com.uade.tpo.cars_e_commerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Order;
import com.uade.tpo.cars_e_commerce.entity.OrderItem;
import com.uade.tpo.cars_e_commerce.entity.dto.OrderDTO;
import com.uade.tpo.cars_e_commerce.entity.dto.OrderItemDTO;
import com.uade.tpo.cars_e_commerce.entity.dto.OrderUserDTO;
import com.uade.tpo.cars_e_commerce.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderRepository orderRepository;


    private OrderDTO convertToDTO(Order order) {
        OrderUserDTO userDTO = new OrderUserDTO(
                order.getUser().getName(),
                order.getUser().getSurname(),
                order.getUser().getPhone_number(),
                order.getUser().getHome_address()
        );

        return OrderDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .total(order.getTotal())
                .user(userDTO)
                .items(order.getItems().stream()
                        .map(this::convertToItemDTO)
                        .collect(Collectors.toList()))
                .build();
    }

 
    private OrderItemDTO convertToItemDTO(OrderItem item) {
        return OrderItemDTO.builder()
                .carId(item.getCar().getCarId())
                .carModel(item.getCar().getModelName())
                .carPrice(item.getCar().getPrice())
                .quantity(item.getQuantity())
                .total(item.getSubtotal())
                .build();
    }

    @Override
    public List<OrderDTO> getOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(long orderId) {
        Order order = orderRepository.findById(orderId);
        return convertToDTO(order);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

}