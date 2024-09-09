package com.uade.tpo.cars_e_commerce.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.dto.CarRequest;
import com.uade.tpo.cars_e_commerce.exceptions.CarDuplicateException;
import com.uade.tpo.cars_e_commerce.exceptions.CarNotFoundException;

public interface CarService {

    List<Car> getCars();

    Optional<Car> getCarById(Long carId);

    void deleteCar(Long carId) throws CarNotFoundException;

    Car createCar(CarRequest carRequest) throws CarDuplicateException;

    List<Car> getCarByManufacturer(String manufacturer) throws CarNotFoundException;

    List<Car> getCarByPrice(Double price) throws CarNotFoundException;

    //List<Car> getCarByPriceRange(Double price_min, Double price_max) throws CarNotFoundException;

    List<Car> getCarByColor(String Color) throws CarNotFoundException;

    List<Car> getCarByModelName(String modelName) throws CarNotFoundException;

    List<Car> getCarByModelYear(int modelYear) throws CarNotFoundException;
    
    Car updateManufacturer(Long carId, String manufacturer) throws CarNotFoundException;

    Car updateColor(Long carId, String color) throws CarNotFoundException;

    Car updateStock(Long carId, Integer stock) throws CarNotFoundException;

    Car updatePrice(Long carId, Double price) throws CarNotFoundException;

    Car updateModelYear(Long carId, Integer modelYear) throws CarNotFoundException;

    Car updateModelName(Long carId, String modelName) throws CarNotFoundException;

}