package com.uade.tpo.cars_e_commerce.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.entity.CarritoItem;
import com.uade.tpo.cars_e_commerce.entity.Order;
import com.uade.tpo.cars_e_commerce.entity.OrderItem;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.CarRepository;
import com.uade.tpo.cars_e_commerce.repository.CarritoItemRepository;
import com.uade.tpo.cars_e_commerce.repository.CarritoRepository;
import com.uade.tpo.cars_e_commerce.repository.OrderItemRepository;
import com.uade.tpo.cars_e_commerce.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Override
    public Carrito getCart(Long id) throws ResourceNotFoundException {
        return carritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
    }

@Override
public Carrito clearCart(Long id) throws ResourceNotFoundException {
    Carrito carrito = carritoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
    carrito.getItems().clear();
    carrito.setTotal(0.0);

    return carritoRepository.save(carrito);
}


    @Override
    public Double getTotalPrice(Long id) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
        
        return calculateTotal(carrito.getItems());
    }
    
    @Override
    public Carrito addProduct(Long cartId, Long productId) throws ResourceNotFoundException {

        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));
    
        Car car = carRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for this id :: " + productId));
    
        Optional<CarritoItem> existingItem = carrito.getItems().stream()
                .filter(item -> item.getCar().getCarId().equals(productId))
                .findFirst();
    
        if (existingItem.isPresent()) {
            CarritoItem item = existingItem.get();

            if (item.getQuantity() + 1 > car.getStock()) {
                throw new IllegalArgumentException("No hay cantidad suficiente en stock para el producto con id :: " + productId);
            }
    
            item.setQuantity(item.getQuantity() + 1);
            carritoItemRepository.save(item);
        } else {
            if (1 > car.getStock()) {
                throw new IllegalArgumentException("No hay cantidad suficiente en stock para el producto con id :: " + productId);
            }
    
            CarritoItem newItem = CarritoItem.builder()
                    .car(car)
                    .quantity(1L)
                    .carrito(carrito)
                    .build();
            carrito.getItems().add(newItem);
            carritoItemRepository.save(newItem);
        }
        carrito.setTotal(calculateTotal(carrito.getItems()));
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito deleteProduct(Long cartId, Long productId) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));

        CarritoItem item = carrito.getItems().stream()
                .filter(i -> i.getCar().getCarId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in the cart for this id :: " + productId));

        carrito.getItems().remove(item);
        carritoItemRepository.delete(item);

        carrito.setTotal(calculateTotal(carrito.getItems()));
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito decreaseProduct(Long cartId, Long productId) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));

        CarritoItem item = carrito.getItems().stream()
                .filter(i -> i.getCar().getCarId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in the cart for this id :: " + productId));

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            carritoItemRepository.save(item);
        } else {
            carrito.getItems().remove(item);
            carritoItemRepository.delete(item);
        }

        carrito.setTotal(calculateTotal(carrito.getItems()));
        return carritoRepository.save(carrito);
    }

@Override
public Carrito increaseProduct(Long cartId, Long productId) throws ResourceNotFoundException {
    Carrito carrito = carritoRepository.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));
    Car car = carRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Car not found for this id :: " + productId));
    Optional<CarritoItem> existingItem = carrito.getItems().stream()
            .filter(item -> item.getCar().getCarId().equals(productId))
            .findFirst();

    if (existingItem.isPresent()) {
        CarritoItem item = existingItem.get();
        if (item.getQuantity() + 1 > car.getStock()) {
            throw new IllegalArgumentException("No hay cantidad suficiente en stock para el producto con id :: " + productId);
        }

        item.setQuantity(item.getQuantity() + 1);
        carritoItemRepository.save(item);
    } else {
        if (1 > car.getStock()) {
            throw new IllegalArgumentException("No hay cantidad suficiente en stock para el producto con id :: " + productId);
        }

        CarritoItem newItem = CarritoItem.builder()
                .car(car)
                .quantity(1L)
                .carrito(carrito)
                .build();
        carrito.getItems().add(newItem);
        carritoItemRepository.save(newItem);
    }
    carrito.setTotal(calculateTotal(carrito.getItems()));
    return carritoRepository.save(carrito);
}


@Override
public Carrito updateProductQuantity(Long cartId, Long productId, Long quantity) throws ResourceNotFoundException {
    Carrito carrito = carritoRepository.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));

    Car car = carRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Car not found for this id :: " + productId));

    Optional<CarritoItem> existingItem = carrito.getItems().stream()
            .filter(item -> item.getCar().getCarId().equals(productId))
            .findFirst();

    if (existingItem.isPresent()) {
        CarritoItem item = existingItem.get();
        
        if (quantity > 0) {
            if (quantity > car.getStock()) {
                throw new IllegalArgumentException("No hay cantidad suficiente en stock para el producto con id :: " + productId);
            }
            
            item.setQuantity(quantity);
            carritoItemRepository.save(item);
        } else {
            carrito.getItems().remove(item);
            carritoItemRepository.delete(item);
        }
    } else if (quantity > 0) {
        if (quantity > car.getStock()) {
            throw new IllegalArgumentException("No hay cantidad suficiente en stock para el producto con id :: " + productId);
        }

        CarritoItem newItem = CarritoItem.builder()
                .car(car)
                .quantity(quantity)
                .carrito(carrito)
                .build();
        carrito.getItems().add(newItem);
        carritoItemRepository.save(newItem);
    }
    carrito.setTotal(calculateTotal(carrito.getItems()));
    return carritoRepository.save(carrito);
}


    private Double calculateTotal(List<CarritoItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getCar().getPrice() * item.getQuantity())
                .sum();
    }
    
    @Transactional
    @Override
    public Carrito checkoutCarrito(Long cartId) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));

        Order order = Order.builder()
            .user(carrito.getUser())
            .total(calculateTotal(carrito.getItems()))
            .status("CREATED")
            .createdAt(Timestamp.from(Instant.now()))
            .build();

        List<OrderItem> orderItems = carrito.getItems().stream().map(item -> {
            Car car = item.getCar();
            car.setStock(car.getStock() - item.getQuantity().intValue()); //Baja el stock del producto
            carRepository.save(car); 

            OrderItem orderItem = OrderItem.builder()
                .order(order)
                .car(item.getCar())
                .quantity(item.getQuantity())
                .price(item.getSubtotal())
                .build();
            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        clearCart(cartId);

        return carrito;
    }
}