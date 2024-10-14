package com.uade.tpo.cars_e_commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.entity.dto.CarritoDTO;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
 import com.uade.tpo.cars_e_commerce.service.CarritoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("carrito")
public class CarritoController {

    @Autowired
    private final CarritoService carritoService;

    @GetMapping("/my-cart")
    public ResponseEntity<CarritoDTO> getCart(@RequestParam Long cartId) {
        try {
            CarritoDTO cartDTO = carritoService.getCartDTO(cartId);
            return ResponseEntity.ok(cartDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<CarritoDTO> clearCart (@RequestParam Long cartId) {
        try {
            CarritoDTO cartDTO = carritoService.clearCart(cartId);
            return ResponseEntity.ok(cartDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total-price")
    public ResponseEntity<Double> getTotalPrice(@RequestParam Long cartId) {
        try {
            Double totalPrice = carritoService.getTotalPrice(cartId);
            return ResponseEntity.ok(totalPrice);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/add-product")
    public ResponseEntity<CarritoDTO> addProduct(@RequestParam Long cartId, @RequestParam Long productId) {
        try {
            CarritoDTO cartDTO = carritoService.addProduct(cartId, productId);
            return ResponseEntity.ok(cartDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<CarritoDTO> deleteProduct(@RequestParam Long cartId, @RequestParam Long productId) {
        try {
            CarritoDTO cartDTO = carritoService.deleteProduct(cartId, productId);
            return ResponseEntity.ok(cartDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-product-quantity")
    public ResponseEntity<CarritoDTO> updateProductQuantity(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Long quantity) {
        try {
            CarritoDTO cartDTO = carritoService.updateProductQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(cartDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/decrease-product")
    public ResponseEntity<CarritoDTO> decreaseProduct(@RequestParam Long cartId, @RequestParam Long productId) {
        try {
            CarritoDTO cartDTO = carritoService.decreaseProduct(cartId, productId);
            return ResponseEntity.ok(cartDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/increase-product")
    public ResponseEntity<CarritoDTO> increaseProduct(@RequestParam Long cartId, @RequestParam Long productId) {
        try {
            CarritoDTO cartDTO= carritoService.increaseProduct(cartId, productId);
            return ResponseEntity.ok(cartDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/checkout-from-cart")
    public ResponseEntity<Carrito> checkoutCarrito(@RequestParam Long cartId) {
        try {
            Carrito carrito = carritoService.checkoutCarrito(cartId);
            return ResponseEntity.ok(carrito);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}   
