package com.uade.tpo.cars_e_commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Cars;
import com.uade.tpo.cars_e_commerce.exceptions.CarDuplicateException;
import com.uade.tpo.cars_e_commerce.exceptions.CarNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Page<Cars> getCars(PageRequest pageRequest) {
        return carRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Cars> getCarById(Long carId) {
        return carRepository.findById(carId);
    }

    @Override
    public Cars createCar(Cars car) throws CarDuplicateException {
        boolean exists = carRepository.existsByManufacturerAndModelNameAndModelYear(
                car.getManufacturer(),
                car.getModelName(),
                car.getModelYear());

        if (exists) {
            throw new CarDuplicateException();
        }

        return carRepository.save(car);
    }

    public List<Cars> getAllCars() {
        return carRepository.findAll(); 
    }

    @Override
    public List<Cars> getCarByManufacturer(String manufacturer) throws CarNotFoundException {
        return carRepository.findByManufacturer(manufacturer);
    }

    @Override
    public List<Cars> getCarByPrice(Double price) throws CarNotFoundException {
        return carRepository.findByPrice(price);
    }

    @Override
    public List<Cars> getCarByPriceRange(Double price_min,Double price_max) throws CarNotFoundException {
        return carRepository.findByRangePrice(price_min, price_max);
    }

    @Override
    public List<Cars> getCarByColor(String color) throws CarNotFoundException{
        return carRepository.findByColor(color);
    }

    @Override
    public List<Cars> getCarByModelName(String modelName) throws CarNotFoundException{
        return carRepository.findByModelName(modelName);
    }

    @Override
    public List<Cars> getCarByModelYear(int modelYear) throws CarNotFoundException{
        return carRepository.findByModelYear(modelYear);
    }
}