package com.uade.tpo.cars_e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.cars_e_commerce.entity.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long>{
    Optional<Carrito> findByUserId(Long userId);
    Optional<Carrito> findByCarritoId(Long carritoId);
    
}
