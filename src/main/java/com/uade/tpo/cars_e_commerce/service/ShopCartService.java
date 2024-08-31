vpackage com.uade.tpo.cars_e_commerce.service;

import com.uade.tpo.cars_e_commerce.entity.ShopCart;


public interface ShopCartService {
    ShopCart getCart(Long id);
    ShopCart clearCart(Long id);
    Double getTotalPrice(Long id);
    Long initializeNewCart();
}