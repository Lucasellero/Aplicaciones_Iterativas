package com.uade.tpo.cars_e_commerce.controllers.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor

public class CarWithImageResponse {

    private Long id;
    private String modelName;
    private String manufacturer;
    private int modelYear;
    private String color;
    private double price;
    private byte[] image; // Imagen codificada en base64
}

