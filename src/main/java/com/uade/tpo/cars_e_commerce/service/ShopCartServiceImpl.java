package com.uade.tpo.cars_e_commerce.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.ShopCart;
import com.uade.tpo.cars_e_commerce.entity.ShopCartLine;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.ShopCartLineRepository;
import com.uade.tpo.cars_e_commerce.repository.ShopCartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    private final ShopCartRepository shopCartRepository;
    @Autowired
    private final ShopCartLineRepository shopCartLineRepository;
    @Autowired
    private final CarService carService;
    //private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public ShopCart getCart(Long id) throws ResourceNotFoundException{
        ShopCart cart = shopCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
        
        Double totalAmount = cart.getTotalAmount(); //Arranca en cero el total
        cart.setTotalAmount(totalAmount); // Pone el total en el carrito
        return shopCartRepository.save(cart);
    }

    @Override
    public ShopCart clearCart(Long id) {
        ShopCart cart = getCart(id);
        shopCartLineRepository.deleteAllByShopCart_ShopCartid(id);
        cart.getShopCartLine().clear(); // Limpia las lineas del carrito.
        shopCartRepository.deleteById(id);
        return cart;
    }

    @Override
    public Double getTotalPrice(Long id) {
        ShopCart cart= getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart(){
        ShopCart newCart = new ShopCart();
        //Long newCartId = cartIdGenerator.incrementAndGet();
        //newCart.setShopCartid(newCartId);
        return shopCartRepository.save(newCart).getShopCartid();
    }
    
}