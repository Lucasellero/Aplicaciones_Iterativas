package com.uade.tpo.cars_e_commerce.service;
import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.entity.CarritoItem;
import com.uade.tpo.cars_e_commerce.entity.dto.CarritoDTO;
import com.uade.tpo.cars_e_commerce.entity.dto.CarritoItemDTO;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
public interface CarritoService {
   CarritoDTO getCart(Long cartId) throws ResourceNotFoundException;
   CarritoDTO clearCart(Long cartId) throws ResourceNotFoundException;
   Double getTotalPrice(Long cartId) throws ResourceNotFoundException;
   CarritoDTO addProduct(Long cartId, Long productId) throws ResourceNotFoundException;
   CarritoDTO deleteProduct(Long cartId, Long productId) throws ResourceNotFoundException;
   CarritoDTO decreaseProduct(Long cartId, Long productId) throws ResourceNotFoundException;
   CarritoDTO increaseProduct(Long cartId, Long productId) throws ResourceNotFoundException;
   CarritoDTO updateProductQuantity(Long cartId, Long productId, Long quantity) throws ResourceNotFoundException;
   Carrito checkoutCarrito(Long cartId) throws ResourceNotFoundException;
   CarritoItemDTO mapToCarritoItemDTO(CarritoItem item);
   CarritoDTO getCartDTO(Long cartId) throws ResourceNotFoundException;

}
