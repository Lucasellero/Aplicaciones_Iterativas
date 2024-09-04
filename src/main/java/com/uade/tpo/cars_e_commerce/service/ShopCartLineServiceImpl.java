package com.uade.tpo.cars_e_commerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.cars_e_commerce.entity.Cars;
import com.uade.tpo.cars_e_commerce.entity.ShopCart;
import com.uade.tpo.cars_e_commerce.entity.ShopCartLine;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.ShopCartLineRepository;
import com.uade.tpo.cars_e_commerce.repository.ShopCartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShopCartLineServiceImpl implements ShopCartLineService {
    @Autowired
    private final ShopCartLineRepository shopCartLineRepository;
    @Autowired
   @Lazy
    private final ShopCartService shopCartService;
    private final ShopCartRepository shopCartRepository; 
    private final CarService carService;

    @Transactional
    @Override
    public void addItemToCart(Long cartId, Long productId, Long quantity) {
        ShopCart cart = shopCartService.getCart(cartId);
        Cars car = carService.getCarById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        ShopCartLine cartItem = cart.getShopCartLine()
                .stream()
                .filter(item -> item.getCar().getCarId().equals(productId))
                .findFirst()
                .orElse(new ShopCartLine());

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
        shopCartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        ShopCart cart = shopCartService.getCart(cartId);
        ShopCartLine itemToRemove = cart.getShopCartLine()
            .stream()
            .filter(item -> item.getCar().getCarId().equals(productId))
            .findFirst().orElseThrow(()-> new ResourceNotFoundException("Auto no encontrado"));
        cart.removeItem(itemToRemove);
        shopCartRepository.save(cart);
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
                item.setTotalPrice();
                shopCartLineRepository.save(item);
            });

        cart.updateTotalAmount();
        shopCartRepository.save(cart);
    }

    @Override
    public ShopCartLine getCartItem(Long cartId, Long productId) {
        ShopCart cart = shopCartService.getCart(cartId);
        return cart.getShopCartLine()
            .stream()
            .filter(item -> item.getCar().getCarId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado"));
    }
}