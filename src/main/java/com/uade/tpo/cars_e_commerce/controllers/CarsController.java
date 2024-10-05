package com.uade.tpo.cars_e_commerce.controllers;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCar(@RequestParam Long carId) {
        try {
            carService.deleteCar(carId);
            return ResponseEntity.ok("El auto con ID " + carId + " ha sido borrado exitosamente.");
                } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping("/carId")
    public ResponseEntity<Car> getCarById(@RequestParam Long carId) {
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

    @GetMapping("/manufacturer")
    public ResponseEntity<List<Car>> getCarByManufacturer(@RequestParam String manufacturer) throws CarNotFoundException {
        List<Car> result = carService.getCarByManufacturer(manufacturer);
        if (result.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Car>> getCarByPrice(@RequestParam Double price) throws CarNotFoundException {
        List<Car> result = carService.getCarByPrice(price);
        if (result.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }


    
    @GetMapping("/color")
    public ResponseEntity<List<Car>> getCarByColor(@RequestParam String color) throws CarNotFoundException {
        List<Car> result = carService.getCarByColor(color);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/model")
    public ResponseEntity<List<Car>> getCarByModelName(@RequestParam String model_name) throws CarNotFoundException {
        List<Car> result = carService.getCarByModelName(model_name);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/year")
    public ResponseEntity<List<Car>> getCarByModelYear(@RequestParam int model_year) throws CarNotFoundException {
        List<Car> result = carService.getCarByModelYear(model_year);
        if (result.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    //-----------------------------------MODIFICACIONES------------------------------------------------------------
    
    @PutMapping("/update/manufacturer")
    public ResponseEntity<Car> updateManufacturer(@RequestParam Long carId, @RequestParam String manufacturer) throws CarNotFoundException {
        Car result = carService.updateManufacturer(carId, manufacturer);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update/color")
    public ResponseEntity<Car> updateColor(@RequestParam Long carId, @RequestParam String color) throws CarNotFoundException {
        Car result = carService.updateColor(carId, color);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update/modelYear")
    public ResponseEntity<Car> updateModelYear(@RequestParam Long carId, @RequestParam Integer modelYear) throws CarNotFoundException  {
        Car result = carService.updateModelYear(carId, modelYear);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/update/modelName")
    public ResponseEntity<Car> updateModelName(@RequestParam Long carId, @RequestParam String modelName) throws CarNotFoundException  {
        Car result = carService.updateModelName(carId, modelName);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/update/price")
    public ResponseEntity<Car> updatePrice(@RequestParam Long carId, @RequestParam Double price)  throws CarNotFoundException {
        Car result = carService.updatePrice(carId, price);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/update/stock")
    public ResponseEntity<Car> updateStock(@RequestParam Long carId, @RequestParam Integer stock) throws CarNotFoundException  {
        Car result = carService.updateStock(carId, stock);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    
    @PutMapping("/update/set-discount")
    public ResponseEntity<Car> updateDiscount(@RequestParam Long carId, @RequestParam Double discount)  throws CarNotFoundException {
        Car result = carService.updateDiscount(carId, discount);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
    


    


