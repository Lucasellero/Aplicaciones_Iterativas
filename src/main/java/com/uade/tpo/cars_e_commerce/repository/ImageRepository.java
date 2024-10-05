package com.uade.tpo.cars_e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
        
        Optional<Image> findById(Long id);
        Optional<Image> findByCar(Car car);
        
}
