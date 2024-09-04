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
    public List<Cars> getCars() {
        return carRepository.findAll();
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

    @Override
    public void deleteCar(Long carId) throws CarNotFoundException {
        if (!carRepository.existsById(carId)) {
            throw new CarNotFoundException();
        }
        carRepository.deleteById(carId);
    }


    //Metodo para actualizar un auto/producto 
    public Cars updateCar(Long carId, Cars updatedCar) {
        Optional<Cars> existingCarOptional = carRepository.findById(carId);

        if (existingCarOptional.isPresent()) {
            Cars existingCar = existingCarOptional.get();

            // Actualizar los campos del auto
            existingCar.setManufacturer(updatedCar.getManufacturer());
            existingCar.setModelName(updatedCar.getModelName());
            existingCar.setModelYear(updatedCar.getModelYear());
            existingCar.setColor(updatedCar.getColor());
            existingCar.setPrice(updatedCar.getPrice());
            existingCar.setStock(updatedCar.getStock());

            // Guardar los cambios
            return carRepository.save(existingCar);
        }

        return null;
    }
    
}