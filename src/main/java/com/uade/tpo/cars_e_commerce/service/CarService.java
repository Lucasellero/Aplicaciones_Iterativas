package com.uade.tpo.cars_e_commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.cars_e_commerce.entity.Cars;
import com.uade.tpo.cars_e_commerce.exceptions.CarDuplicateException;
import com.uade.tpo.cars_e_commerce.exceptions.CarNotFoundException;

public interface CarService {

    Page<Cars> getCars(PageRequest pageRequest);

    List<Cars> getAllCars();

    Optional<Cars> getCarById(Long carId);

    Cars createCar(Cars car) throws CarDuplicateException;

    List<Cars> getCarByManufacturer(String manufacturer) throws CarNotFoundException;

    List<Cars> getCarByPrice(Double price) throws CarNotFoundException;

    List<Cars> getCarByPriceRange(Double price_min, Double price_max) throws CarNotFoundException;

    List<Cars> getCarByColor(String Color) throws CarNotFoundException;

    List<Cars> getCarByModelName(String modelName) throws CarNotFoundException;

    List<Cars> getCarByModelYear(int modelYear) throws CarNotFoundException;
}