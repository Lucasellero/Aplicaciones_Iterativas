package com.uade.tpo.cars_e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.tpo.cars_e_commerce.entity.ShopCartLine;

public interface ShopCartLineRepository extends JpaRepository<ShopCartLine, Long> {
    void deleteAllByCartId(Long id);
    
}
