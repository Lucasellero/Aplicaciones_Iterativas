package com.uade.tpo.cars_e_commerce.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserDTO {
    private String name;
    private String surname;
    private String home_address;
    private String phone_number;
    
}
