package com.uade.tpo.cars_e_commerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.CarRepository;
import com.uade.tpo.cars_e_commerce.repository.CarritoRepository;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private CarRepository carRepository;

    private Map<Car, Long> items = new HashMap<>();

    @Override ///quedo
    public Carrito getCart(Long id) throws ResourceNotFoundException {
        return carritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
    }

    @Override /// quedo
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
        
        Double total = 0.0;
        for (Map.Entry<Car, Long> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue(); // Precio * cantidad
        }
        return total;
    }


    @Override 
    public Carrito addProduct(Long cartId, Long productId) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));
        
        Map<Car, Long> items = carrito.getItems();
        
        Car car = carRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for this id :: " + productId));

        boolean exists = items.keySet().stream()
                .anyMatch(c -> c.getCarId().equals(productId));
        
        if (exists) {
            Car existingCar = items.keySet().stream()
                .filter(c -> c.getCarId().equals(productId))
                .findFirst()
                .orElse(null);

            if (existingCar != null) {
                items.put(existingCar, items.get(existingCar) + 1);
            }
        } else {
            items.put(car, 1L);
        }
        carrito.setTotal(carrito.getTotal());
        
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito deleteProduct(Long cartId, Long productId) throws ResourceNotFoundException {

        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));

        Map <Car , Long> items = carrito.getItems();
        //verificamos si existe
        Car car = items.keySet().stream()
                .filter(c -> c.getCarId().equals(productId))
                .findFirst()
                .orElse(null);
                
        if (car != null) {
            items.remove(car);
        } else {
            throw new ResourceNotFoundException("Product not found in the cart for this id :: " + productId);
        }
        carrito.setTotal(carrito.getTotal());
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito decreaseProduct(Long cartId, Long productId) throws ResourceNotFoundException {
        
        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));

        Map<Car, Long> items = carrito.getItems();
    
        Car car = items.keySet().stream()
                .filter(c -> c.getCarId().equals(productId))
                .findFirst()
                .orElse(null);

        if (car != null) {
            Long quantity = items.get(car);
            if (quantity > 1) {
                items.put(car, quantity - 1);
            } else {
                items.remove(car);
            } 
        } else {
            throw new ResourceNotFoundException("Product not found in the cart for this id :: " + productId);
        }
        carrito.setTotal(carrito.getTotal());
        return carritoRepository.save(carrito);
        }


    @Override
    public Carrito increaseProduct(Long cartId, Long productId) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));

                Map<Car, Long> items = carrito.getItems();

                Car car = items.keySet().stream()
                        .filter(c -> c.getCarId().equals(productId))
                        .findFirst()
                        .orElse(null);
            
                if (car != null) {
                    items.put(car, items.get(car) + 1);
                } else {
                    car = carRepository.findById(productId)
                            .orElseThrow(() -> new ResourceNotFoundException("Car not found for this id :: " + productId));
                            // agrega el producto al carrito con una cantidad inicial de 1            
                            items.put(car, 1L);
                }
                carrito.setTotal(carrito.getTotal());
            
                return carritoRepository.save(carrito);
            }


    @Override
    public Carrito updateProductQuantity(Long cartId, Long productId, Long quantity) throws ResourceNotFoundException {
        Carrito carrito = carritoRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + cartId));
    
        //obtenemos los items
        Map<Car, Long> items = carrito.getItems();
        Car car = items.keySet().stream()
                .filter(c -> c.getCarId().equals(productId))
                .findFirst()
                .orElse(null);
        if (car != null) {
            if (quantity > 0) {
                items.put(car, quantity);
            } else {
                items.remove(car);
            }
        } else {
            car = carRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Car not found for this id :: " + productId));
            if (quantity > 0) {
                items.put(car, quantity);
            }
        }
        carrito.setTotal(carrito.getTotal());
        return carritoRepository.save(carrito);
    }    
}
