package com.uade.tpo.cars_e_commerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.entity.dto.AddItemRequest;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.service.ShopCartLineService;
import com.uade.tpo.cars_e_commerce.service.ShopCartService;
import com.uade.tpo.cars_e_commerce.service.UserService;
import com.uade.tpo.cars_e_commerce.entity.ShopCart;
import com.uade.tpo.cars_e_commerce.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("cartItems")
public class ShopCartLineController {
    private final ShopCartLineService shopCartLineService;
    private final ShopCartService shopCartService;
    private final UserService userSer;
    
    @PostMapping("/item/add")
    public ResponseEntity<Object> addItemToCart(@RequestBody AddItemRequest addItemRequest, 
                                                @AuthenticationPrincipal UserDetails userDetails) {
        try {  
            Long userId = userSer.findByUsername(userDetails.getUsername()).getId();
            ShopCart shopCart = shopCartService.getCartByUserId(userId);
         if (shopCart == null) {
            shopCart = new ShopCart();
            User user = userSer.findByUsername(userDetails.getUsername());
            shopCart.setUserId(userId);
            shopCart.setUser(user);
            shopCartService.save(shopCart);
        }
            shopCartLineService.addItemToCart(userId, addItemRequest.getProductId(), addItemRequest.getQuantity());
            return ResponseEntity.ok("Item added to cart");
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
    public ResponseEntity<Object> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam Long quantity) {
        try {
            shopCartLineService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}