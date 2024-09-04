package com.uade.tpo.cars_e_commerce.controllers;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.entity.Cars;
import com.uade.tpo.cars_e_commerce.entity.dto.CarRequest;
import com.uade.tpo.cars_e_commerce.exceptions.CarDuplicateException;
import com.uade.tpo.cars_e_commerce.exceptions.CarNotFoundException;
import com.uade.tpo.cars_e_commerce.service.CarService;


@RestController
@RequestMapping("cars")
public class CarsController {

    @Autowired
    private CarService carService;

    @PostMapping ("/create")
    public ResponseEntity<Object> createCar(@RequestBody CarRequest carRequest) throws CarDuplicateException {
        try{
            Cars car = new Cars();
            car.setManufacturer(carRequest.getManufacturer());
            car.setModelName(carRequest.getModelName());
            car.setModelYear(carRequest.getModelYear());
            car.setColor(carRequest.getColor());
            car.setPrice(carRequest.getPrice());
            car.setStock(carRequest.getStock());
            Cars result = carService.createCar(car);
            URI location = URI.create("/cars/" + result.getCarId());
            return ResponseEntity.created(location).body(result);
        }catch (CarDuplicateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Long carId) {
        try {
            carService.deleteCar(carId);
            return ResponseEntity.ok("El auto con ID " + carId + " ha sido borrado exitosamente.");
                } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<Cars>> getAllCars() {
        List<Cars> result = carService.getCars();
        return ResponseEntity.ok(result);
    }


    
    

    //FILTROS
    @GetMapping("/manufacturer/{manufacturer}")
    public ResponseEntity<List<Cars>> getCarByManufacturer(@PathVariable String manufacturer) throws CarNotFoundException {
        List<Cars> result = carService.getCarByManufacturer(manufacturer);
        if (result.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Cars>> getCarByPrice(@PathVariable Double price) throws CarNotFoundException {
        List<Cars> result = carService.getCarByPrice(price);
        if (result.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/price-range/{price_min}/{price_max}")
    public ResponseEntity<List<Cars>> getCarByPriceRange(@PathVariable Double price_min, @PathVariable Double price_max) throws CarNotFoundException {
        List<Cars> result = carService.getCarByPriceRange(price_min, price_max);
        if (result.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/color/{color}")
    public ResponseEntity<List<Cars>> getCarByColor(@PathVariable String color) throws CarNotFoundException {
        List<Cars> result = carService.getCarByColor(color);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/model/{modelName}")
    public ResponseEntity<List<Cars>> getCarByModelName(@PathVariable String modelName) throws CarNotFoundException {
        List<Cars> result = carService.getCarByModelName(modelName);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/year/{model_year}")
    public ResponseEntity<List<Cars>> getCarByModelYear(@PathVariable int modelYear) throws CarNotFoundException {
        List<Cars> result = carService.getCarByModelYear(modelYear);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    //-----------------------------------MODIFICACIONES------------------------------------------------------------
    @PostMapping("/update/manufacturer/{carId}")
    public ResponseEntity<Cars> updateManufacturer(@PathVariable Long carId, @RequestBody CarRequest carRequest) {
        Cars updatedCar = new Cars();
        updatedCar.setManufacturer(carRequest.getManufacturer());

        Cars result = carService.updateCar(carId, updatedCar);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/update/color/{carId}")
    public ResponseEntity<Cars> updateColor(@PathVariable Long carId, @RequestBody CarRequest carRequest) {
        Cars updatedCar = new Cars();
        updatedCar.setColor(carRequest.getColor());

        Cars result = carService.updateCar(carId, updatedCar);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/update/modelYear/{carId}")
    public ResponseEntity<Cars> updateModelYear(@PathVariable Long carId, @RequestBody CarRequest carRequest) {
        Cars updatedCar = new Cars();
        updatedCar.setModelYear(carRequest.getModelYear());

        Cars result = carService.updateCar(carId, updatedCar);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/update/price/{carId}")
    public ResponseEntity<Cars> updatePrice(@PathVariable Long carId, @RequestBody CarRequest carRequest) {
        Cars updatedCar = new Cars();
        updatedCar.setPrice(carRequest.getPrice());

        Cars result = carService.updateCar(carId, updatedCar);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/update/stock/{carId}")
    public ResponseEntity<Cars> updateStock(@PathVariable Long carId, @RequestBody CarRequest carRequest) {
        Cars updatedCar = new Cars();
        updatedCar.setStock(carRequest.getStock());

        Cars result = carService.updateCar(carId, updatedCar);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

    


    


