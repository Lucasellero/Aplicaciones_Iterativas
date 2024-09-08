package com.uade.tpo.cars_e_commerce.service;

import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.entity.Order;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.CarritoRepository;
import com.uade.tpo.cars_e_commerce.repository.OrderRepository;
import com.uade.tpo.cars_e_commerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

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

        orderRepository.save(order);

        carritoRepository.delete(carrito); //Chequear si se borra el carrito o solo si se lo limpia

        return order;
    }
}