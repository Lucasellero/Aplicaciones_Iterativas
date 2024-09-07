package com.uade.tpo.cars_e_commerce.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.entity.CarritoItem;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.CarRepository;
import com.uade.tpo.cars_e_commerce.repository.CarritoItemRepository;
import com.uade.tpo.cars_e_commerce.repository.CarritoRepository;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    private Map<Car, Long> items = new HashMap<>();

    @Override
    public Carrito getCart(Long id) throws ResourceNotFoundException {
        return carritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
    }

    @Override
    public Carrito clearCart(Long id) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
        items.clear();
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
            item.setQuantity(item.getQuantity() + 1);
            carritoItemRepository.save(item);
        } else {
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
            item.setQuantity(item.getQuantity() + 1);
            carritoItemRepository.save(item);
        } else {
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
                item.setQuantity(quantity);
                carritoItemRepository.save(item);
            } else {
                carrito.getItems().remove(item);
                carritoItemRepository.delete(item);
            }
        } else if (quantity > 0) {
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
}
