package com.uade.tpo.cars_e_commerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.entity.ShopCartLine;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.service.ShopCartLineService;
import com.uade.tpo.cars_e_commerce.service.ShopCartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("cartItems")
public class ShopCartLineController {
    private final ShopCartLineService shopCartLineService;
    private final ShopCartService shopCartService;
    
    @PostMapping("/item/add")
    public ResponseEntity<Object> addItemToCart(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Long quantity) {       
        try {      
            if  (cartId == null) {
                cartId = shopCartService.initializeNewCart();
            }
            shopCartLineService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<Object> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            shopCartLineService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    public ResponseEntity<Object> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam Long quantity) {
        try {
            shopCartLineService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}