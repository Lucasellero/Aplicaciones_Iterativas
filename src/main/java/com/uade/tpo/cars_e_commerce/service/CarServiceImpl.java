package com.uade.tpo.cars_e_commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.dto.CarRequest;
import com.uade.tpo.cars_e_commerce.exceptions.CarDuplicateException;
import com.uade.tpo.cars_e_commerce.exceptions.CarNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.CarRepository;


@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarById(Long carId) {
        return carRepository.findById(carId);
    }


    @Override
    public Car createCar(CarRequest carRequest) throws CarDuplicateException {
        boolean exists = carRepository.existsByManufacturerAndModelNameAndModelYear(
                carRequest.getManufacturer(),
                carRequest.getModelName(),
                carRequest.getModelYear());

        if (exists) {
            throw new CarDuplicateException();
        }

        Car car = new Car();
        car.setManufacturer(carRequest.getManufacturer());
        car.setModelName(carRequest.getModelName());
        car.setModelYear(carRequest.getModelYear());
        car.setColor(carRequest.getColor());
        car.setPrice(carRequest.getPrice());
        car.setStock(carRequest.getStock());
        car.setUrl(carRequest.getUrl());

        return carRepository.save(car);
    }

    @Override
    public List<Car> getCarByManufacturer(String manufacturer) throws CarNotFoundException {
        return carRepository.findByManufacturer(manufacturer);
    }

    @Override
    public List<Car> getCarByPrice(Double price) throws CarNotFoundException {
        return carRepository.findByPrice(price);
    }

    @Override
    public List<Car> getCarByColor(String color) throws CarNotFoundException{
        return carRepository.findByColor(color);
    }

    @Override
    public List<Car> getCarByModelName(String modelName) throws CarNotFoundException{
        return carRepository.findByModelName(modelName);
    }

    @Override
    public List<Car> getCarByModelYear(int modelYear) throws CarNotFoundException{
        return carRepository.findByModelYear(modelYear);
    }

    @Override
    public void deleteCar(Long carId) throws CarNotFoundException {
        if (!carRepository.existsById(carId)) {
            throw new CarNotFoundException("Car not found for this id :: " + carId);
        }
        carRepository.deleteById(carId);
    }

    @Override
    public Car updateManufacturer(Long carId, String manufacturer) throws CarNotFoundException{
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            existingCar.setManufacturer(manufacturer);
            return carRepository.save(existingCar); 
        } else {
            return null;
        }
    }

    @Override
    public Car updateColor(Long carId, String color) throws CarNotFoundException{
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            existingCar.setColor(color); 
            return carRepository.save(existingCar); 
        } else {
            return null;
        }
    }

    @Override
    public Car updateModelYear(Long carId, Integer modelYear) throws CarNotFoundException {
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            existingCar.setModelYear(modelYear); 
            return carRepository.save(existingCar); 
        } else {
            return null; 
        }
    }

    @Override
    public Car updateModelName(Long carId, String modelName) throws CarNotFoundException {
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            existingCar.setModelName(modelName); 
            return carRepository.save(existingCar); 
        } else {
            return null; 
        }
    }

    @Override
    public Car updateStock(Long carId, Integer stock) throws CarNotFoundException{
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            existingCar.setStock(stock);
            return carRepository.save(existingCar);
        } else {
            return null; 
        }
    }

    @Override
    public Car updatePrice(Long carId, Double price) throws CarNotFoundException{
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            existingCar.setPrice(price); 
            return carRepository.save(existingCar); 
        } else {
            return null; 
        }
    }

    @Override
    public Car updateDiscount(Long carId, Double discount) throws CarNotFoundException{
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            existingCar.setDiscount(discount); 
            return carRepository.save(existingCar); 
        } else {
            return null; 
        }
    }

}