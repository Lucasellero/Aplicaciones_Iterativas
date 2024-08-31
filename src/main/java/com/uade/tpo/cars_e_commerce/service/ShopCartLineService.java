package com.uade.tpo.cars_e_commerce.service;

import com.uade.tpo.cars_e_commerce.entity.ShopCartLine;

public interface ShopCartLineService {

    void addItemToCart(Long cartId, Long productId, Long quantity);

    void removeItemFromCart(Long cartId, Long productId);
    
    void updateItemQuantity(Long cartId, Long productId, Long quantity);

    ShopCartLine getCartItem(Long cartId, Long productId);
    
}