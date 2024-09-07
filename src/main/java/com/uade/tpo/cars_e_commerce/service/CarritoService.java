package com.uade.tpo.cars_e_commerce.service;

import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;

public interface CarritoService {
 
   Carrito getCart(Long cartId) throws ResourceNotFoundException;
   Carrito clearCart(Long cartId) throws ResourceNotFoundException;
   Double getTotalPrice(Long cartId) throws ResourceNotFoundException;
   Carrito addProduct(Long cartId, Long productId) throws ResourceNotFoundException;
   Carrito deleteProduct(Long cartId, Long productId) throws ResourceNotFoundException;
   Carrito decreaseProduct(Long cartId, Long productId) throws ResourceNotFoundException;
   Carrito increaseProduct(Long cartId, Long productId) throws ResourceNotFoundException;
   Carrito updateProductQuantity(Long cartId, Long productId, Long quantity) throws ResourceNotFoundException;
}
