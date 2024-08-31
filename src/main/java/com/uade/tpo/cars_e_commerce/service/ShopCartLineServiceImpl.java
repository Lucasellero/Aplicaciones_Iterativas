package com.uade.tpo.cars_e_commerce.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.ShopCart;
import com.uade.tpo.cars_e_commerce.entity.ShopCartLine;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.ShopCartLineRepository;
import com.uade.tpo.cars_e_commerce.repository.ShopCartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopCartLineServiceImpl implements ShopCartLineService {
    @Autowired
    private final ShopCartLineRepository shopCartLineRepository;
    @Autowired
    private final ShopCartRepository ShopCartRepository; 
    @Autowired
    private final CarService carService;
    @Autowired
    private final ShopCartService shopCartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, Long quantity) {
        ShopCart cart = shopCartService.getCart(cartId);
        Car car = carService.getCarById(productId).orElseThrow(() -> new ResourceNotFoundException("car not found"));
        ShopCartLine cartItem = cart.getShopCartLine()
                                .stream()
                                .filter(item -> item.getCar().getCarId().equals(productId))
                                .findFirst().orElse(new ShopCartLine());
        if (cartItem.getCar() == null) {
            cartItem.setShopCart(cart);
            cartItem.setCar(car);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(car.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        shopCartLineRepository.save(cartItem);
        ShopCartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        ShopCart cart = shopCartService.getCart(cartId);
        ShopCartLine itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        ShopCartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, Long quantity) {
        ShopCart cart = shopCartService.getCart(cartId);
        cart.getShopCartLine()
            .stream()
            .filter(item -> item.getCar().getCarId().equals(productId))
            .findFirst()
            .ifPresent(item -> {
                item.setQuantity(quantity);
                item.setUnitPrice(item.getCar().getPrice()); // redundante
                item.setTotalPrice();
                shopCartLineRepository.save(item);
            });

            Double totalAmount = cart.getTotalAmount();
            cart.setTotalAmount(totalAmount);
            ShopCartRepository.save(cart);
    }

    @Override
    public ShopCartLine getCartItem(Long cartId, Long productId) {
        ShopCart cart = shopCartService.getCart(cartId);
        return cart.getShopCartLine()
            .stream()
            .filter(item -> item.getCar().getCarId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("car not found"));
    }

    // TODO Decrease quantity of a product in the cart one by one. If quantity is 1, remove the product from the cart.
    // TODO Increase quantity of a product in the cart one by one.

    
}
