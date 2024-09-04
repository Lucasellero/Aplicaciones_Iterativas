package com.uade.tpo.cars_e_commerce.entity.dto;

import lombok.Data;

@Data
public class CarRequest {
    private Long id;
    private String manufacturer;
    private String modelName;
    private Integer modelYear; 
    private String color;
    private Double price;
    private Integer stock;
    
}
