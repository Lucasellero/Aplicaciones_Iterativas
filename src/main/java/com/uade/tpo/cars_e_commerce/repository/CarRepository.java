package com.uade.tpo.cars_e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.cars_e_commerce.entity.Car;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByManufacturerAndModelNameAndModelYear(String manufacturer, String modelName, Integer modelYear);

    @Query(value = "select c from Cars c where c.manufacturer = ?1")
    List<Car> findByManufacturer(String manufacturer);

    @Query(value = "select c from Cars c where c.price = ?1")
    List<Car> findByPrice(Double price);

    @Query(value = "select c from Cars c where c.price > ?1 and c.price < ?2")
    List<Car> findByRangePrice(Double price_min, Double price_max);

    @Query(value = "select c from Cars c where c.color = ?1")
    List<Car> findByColor(String Color);

    @Query(value = "select c from Cars c where c.modelName = ?1")
    List<Car> findByModelName(String modelName);

    @Query(value = "select c from Cars c where c.modelYear = ?1")
    List<Car> findByModelYear(int modelYear);
}
