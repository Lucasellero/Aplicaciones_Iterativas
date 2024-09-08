package com.uade.tpo.cars_e_commerce.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.entity.Order;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.CarritoRepository;
import com.uade.tpo.cars_e_commerce.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order checkoutCarrito(Long cartId) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));

        Order order = Order.builder()
                .user(carrito.getUser())
                .items(carrito.getItems())
                .total(carrito.getTotal())
                .status("CREATED")
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        carrito.getItems().forEach(item -> item.setOrder(order));

        orderRepository.save(order);

        carritoRepository.delete(carrito);

        return order;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}