package com.uade.tpo.cars_e_commerce.controllers;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.dto.CarRequest;
import com.uade.tpo.cars_e_commerce.exceptions.CarDuplicateException;
import com.uade.tpo.cars_e_commerce.exceptions.CarNotFoundException;
import com.uade.tpo.cars_e_commerce.service.CarService;  


@RestController
@RequestMapping("car")
public class CarsController {

    @Autowired
    private CarService carService;

    @PostMapping("/create")
    public ResponseEntity<Object> createCar(@RequestBody CarRequest carRequest) {
        try {
            Car result = carService.createCar(carRequest);
            URI location = URI.create("/cars/" + result.getCarId());
            return ResponseEntity.created(location).body(result);
        } catch (CarDuplicateException e) {
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

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        try {
            Car car = carService.getCarById(carId)
                                .orElseThrow(() -> new CarNotFoundException("Auto no encontrado"));
            return ResponseEntity.ok(car);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> result = carService.getCars();
        return ResponseEntity.ok(result);
    }



    //-----------------------------------FILTROS------------------------------------------------------------

    @GetMapping("/manufacturer/{manufacturer}")
    public ResponseEntity<List<Car>> getCarByManufacturer(@PathVariable String manufacturer) throws CarNotFoundException {
        List<Car> result = carService.getCarByManufacturer(manufacturer);
        if (result.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Car>> getCarByPrice(@PathVariable Double price) throws CarNotFoundException {
        List<Car> result = carService.getCarByPrice(price);
        if (result.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }


    
    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarByColor(@PathVariable String color) throws CarNotFoundException {
        List<Car> result = carService.getCarByColor(color);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/model/{model_name}")
    public ResponseEntity<List<Car>> getCarByModelName(@PathVariable String model_name) throws CarNotFoundException {
        List<Car> result = carService.getCarByModelName(model_name);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/year/{model_year}")
    public ResponseEntity<List<Car>> getCarByModelYear(@PathVariable int model_year) throws CarNotFoundException {
        List<Car> result = carService.getCarByModelYear(model_year);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    //-----------------------------------MODIFICACIONES------------------------------------------------------------
    
    @PostMapping("/{carId}/update/manufacturer/{manufacturer}")
    public ResponseEntity<Car> updateManufacturer(@PathVariable Long carId, @PathVariable String manufacturer) throws CarNotFoundException {
        Car result = carService.updateManufacturer(carId, manufacturer);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{carId}/update/color/{color}")
    public ResponseEntity<Car> updateColor(@PathVariable Long carId, @PathVariable String color) throws CarNotFoundException {
        Car result = carService.updateColor(carId, color);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{carId}/update/modelYear/{modelYear}")
    public ResponseEntity<Car> updateModelYear(@PathVariable Long carId, @PathVariable Integer modelYear) throws CarNotFoundException  {
        Car result = carService.updateModelYear(carId, modelYear);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/{carId}/update/modelName/{modelName}")
    public ResponseEntity<Car> updateModelName(@PathVariable Long carId, @PathVariable String modelName) throws CarNotFoundException  {
        Car result = carService.updateModelName(carId, modelName);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/{carId}/update/price/{price}")
    public ResponseEntity<Car> updatePrice(@PathVariable Long carId, @PathVariable Double price)  throws CarNotFoundException {
        Car result = carService.updatePrice(carId, price);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/{carId}/update/stock/{stock}")
    public ResponseEntity<Car> updateStock(@PathVariable Long carId, @PathVariable Integer stock) throws CarNotFoundException  {
        Car result = carService.updateStock(carId, stock);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    
    @PostMapping("/{carId}/update/set-discount/{discount}")
    public ResponseEntity<Car> updateDiscount(@PathVariable Long carId, @PathVariable Double discount)  throws CarNotFoundException {
        Car result = carService.updateDiscount(carId, discount);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
    


    


