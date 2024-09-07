package com.uade.tpo.cars_e_commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Car;
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
    public Car createCar(Car car) throws CarDuplicateException {
        boolean exists = carRepository.existsByManufacturerAndModelNameAndModelYear(
                car.getManufacturer(),
                car.getModelName(),
                car.getModelYear());

        if (exists) {
            throw new CarDuplicateException();
        }

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

    /*@Override
    public List<Car> getCarByPriceRange(Double price_min,Double price_max) throws CarNotFoundException {
        return carRepository.findByRangePrice(price_min, price_max);
    }*/

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
            throw new CarNotFoundException();
        }
        carRepository.deleteById(carId);
    }


    //Metodo para actualizar un auto/producto 
    public Car updateCar(Long carId, Car updatedCar) {
        Optional<Car> existingCarOptional = carRepository.findById(carId);

        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();

            existingCar.setManufacturer(updatedCar.getManufacturer());
            existingCar.setModelName(updatedCar.getModelName());
            existingCar.setModelYear(updatedCar.getModelYear());
            existingCar.setColor(updatedCar.getColor());
            existingCar.setPrice(updatedCar.getPrice());
            existingCar.setStock(updatedCar.getStock());

            return carRepository.save(existingCar);
        }
        return null;
    }
    
}