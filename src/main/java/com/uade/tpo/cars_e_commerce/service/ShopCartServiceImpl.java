package com.uade.tpo.cars_e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.ShopCart;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.ShopCartLineRepository;
import com.uade.tpo.cars_e_commerce.repository.ShopCartRepository;
import com.uade.tpo.cars_e_commerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopCartServiceImpl implements ShopCartService {
    
    private final ShopCartRepository shopCartRepository;
    private final ShopCartLineRepository shopCartLineRepository;
    private final CarService carService;
    private final UserRepository userRepository;
    
  

    @Override
    public ShopCart getCart(Long id) throws ResourceNotFoundException{
        ShopCart cart = shopCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
        
        Double totalAmount = cart.getTotalAmount(); //Arranca en cero el total
        cart.setTotalAmount(totalAmount); // Pone el total en el carrito
        return cart;
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
    public ShopCart initializeNewCart(Long userId) {
        ShopCart newCart = new ShopCart();
        newCart.setUserId(userId);
        ShopCart savedCart = shopCartRepository.save(newCart);
        System.out.println("New cart created with ID: " + savedCart.getShopCartid());
        return savedCart;
    }

    @Override
    public ShopCart getCartByUserId(Long userId) {
        return shopCartRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public ShopCart save(ShopCart shopCart) {
        return shopCartRepository.save(shopCart);
    }

    /*
    @Override
    public void addItemToCart(Long userId, Long productId, Long quantity) {
        ShopCart cart = getCartByUserId(userId);
        if (cart == null) {
            cart = initializeNewCart(userId);
        }
        shopCartLineService.addItemToCart(cart.getShopCartid(), productId, quantity);
        cart.updateTotalAmount();
        save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        shopCartLineService.removeItemFromCart(cartId, productId);
        ShopCart cart = getCart(cartId);
        cart.updateTotalAmount();
        save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, Long quantity) {
        shopCartLineService.updateItemQuantity(cartId, productId, quantity);
        ShopCart cart = getCart(cartId);
        cart.updateTotalAmount();
        save(cart);
    } */
}






