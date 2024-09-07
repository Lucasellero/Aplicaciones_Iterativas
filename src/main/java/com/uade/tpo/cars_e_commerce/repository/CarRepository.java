package com.uade.tpo.cars_e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.cars_e_commerce.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByManufacturerAndModelNameAndModelYear(String manufacturer, String modelName, Integer modelYear);

    void deleteById(Long carId);

    @Query(value = "select c from Car c where c.manufacturer = :manufacturer")
    List<Car> findByManufacturer(@Param("manufacturer") String manufacturer);

    @Query(value = "select c from Car c where c.price = :price")
    List<Car> findByPrice(@Param("price") Double price);

    /*@Query(value = "select c from car c where c.price > ?1 and c.price < ?2")
    List<Car> findByRangePrice(Double price_min, Double price_max); */

    @Query(value = "select c from Car c where c.color = :color") 
    List<Car> findByColor(@Param("color") String color);

    @Query(value = "select c from Car c where c.modelName = :modelName")
    List<Car> findByModelName(@Param("modelName") String modelName);

    @Query(value = "select c from Car c where c.modelYear = :modelYear")
    List<Car> findByModelYear(@Param("modelYear") Integer modelYear);    
}


