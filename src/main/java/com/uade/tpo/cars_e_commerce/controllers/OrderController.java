package com.uade.tpo.cars_e_commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.entity.dto.OrderDTO;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.service.OrderService;


@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> result = orderService.getOrders();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/idOrder")
    public ResponseEntity<OrderDTO> getOrderById(@RequestParam long orderId) {
        try {
            OrderDTO order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
        

    @GetMapping("/user/id")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@RequestParam Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}