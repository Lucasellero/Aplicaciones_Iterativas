package com.uade.tpo.cars_e_commerce.service;

import com.uade.tpo.cars_e_commerce.entity.ShopCart;


public interface ShopCartService {
    ShopCart getCart(Long id);
    ShopCart clearCart(Long id);
    Double getTotalPrice(Long id);
    ShopCart initializeNewCart(Long userId);
    ShopCart getCartByUserId(Long userId);
    ShopCart save(ShopCart shopCart);

    /* 
    void addItemToCart(Long userId, Long productId, Long quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, Long quantity);
    */
}