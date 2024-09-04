package com.uade.tpo.cars_e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.cars_e_commerce.entity.ShopCart;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart, Long>{
    Optional<ShopCart> findByUserId(Long userId);
    
}
