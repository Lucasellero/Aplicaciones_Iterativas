package com.uade.tpo.cars_e_commerce.controllers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private Long id;
    private String file;
    
}
