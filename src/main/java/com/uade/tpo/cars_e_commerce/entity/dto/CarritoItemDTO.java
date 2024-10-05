package com.uade.tpo.cars_e_commerce.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarritoItemDTO {
    private String manufacturer;
    private String modelName;
    private Double price;
    private Double subtotal;
    private Long quantity;
}
