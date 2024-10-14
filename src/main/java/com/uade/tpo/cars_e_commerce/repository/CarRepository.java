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

    @Query (value = "DELETE FROM Car c WHERE c.id = :carId")
    List<Car> deleteCarById(@Param("carId") Long carId);

    @Query(value = "select c from Car c where LOWER(c.manufacturer) LIKE LOWER(CONCAT('%', :manufacturer, '%'))")
    List<Car> findByManufacturer(@Param("manufacturer") String manufacturer);
    
    @Query(value = "select c from Car c where c.price = :price")
    List<Car> findByPrice(@Param("price") Double price);

    @Query(value = "select c from Car c where c.color = :color") 
    List<Car> findByColor(@Param("color") String color);

    @Query(value = "select c from Car c where c.modelName = :modelName")
    List<Car> findByModelName(@Param("modelName") String modelName);

    @Query(value = "select c from Car c where c.modelYear = :modelYear")
    List<Car> findByModelYear(@Param("modelYear") Integer modelYear);    

    @Query("SELECT c FROM Car c WHERE " +
           "(:manufacturer IS NULL OR c.manufacturer = :manufacturer) AND " +
           "(:model_name IS NULL OR c.modelName = :model_name) AND " +
           "(:color IS NULL OR c.color = :color) AND " +
           "(:model_year IS NULL OR c.modelYear = :model_year) AND " +
           "(:price IS NULL OR c.price = :price)")
    List<Car> findByFilters(@Param("manufacturer") String manufacturer,
                             @Param("model_name") String model_name,
                             @Param("color") String color,
                             @Param("model_year") Integer model_year,
                             @Param("price") Double price);
}



