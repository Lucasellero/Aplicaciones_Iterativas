package com.uade.tpo.cars_e_commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.service.CarritoService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("carrito")
public class CarritoController {

    @Autowired
    private final CarritoService CarritoService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<Carrito> getCart(@PathVariable Long cartId) {
        try {
            Carrito cart = CarritoService.getCart(cartId);
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cartId}/clear-cart")
    public ResponseEntity<Carrito> clearCart (@PathVariable Long cartId) {
        try {
            Carrito cart = CarritoService.clearCart(cartId);
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<Double> getTotalPrice(@PathVariable Long cartId) {
        try {
            Double totalPrice = CarritoService.getTotalPrice(cartId);
            return ResponseEntity.ok(totalPrice);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cartId}/add-product/{productId}")
    public ResponseEntity<Carrito> addProduct(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            Carrito cart = CarritoService.addProduct(cartId, productId);
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cartId}/delete-product/{productId}")
    public ResponseEntity<Carrito> deleteProduct(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            Carrito cart = CarritoService.deleteProduct(cartId, productId);
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cartId}/update-product/{productId}/{quantity}")
    public ResponseEntity<Carrito> updateProductQuantity(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Long quantity) {
        try {
            Carrito cart = CarritoService.updateProductQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{cartId}/decrease-product/{productId}")
    public ResponseEntity<Carrito> decreaseProduct(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            Carrito cart = CarritoService.decreaseProduct(cartId, productId);
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cartId}/increase-product/{productId}")
    public ResponseEntity<Carrito> increaseProduct(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            Carrito cart = CarritoService.increaseProduct(cartId, productId);
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}   
