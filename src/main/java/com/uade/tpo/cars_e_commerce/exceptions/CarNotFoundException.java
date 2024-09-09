package com.uade.tpo.cars_e_commerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "No se ha encontrado el auto que esta buscando")
public class CarNotFoundException extends Exception {
        public CarNotFoundException(String message) {
        super(message);
    }
}
