package com.uade.tpo.cars_e_commerce.entity.dto;

import lombok.Data;

@Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
public class OrderUserRequest {
    private String email;
    private String name;
    private String surname;
    private String home_address;
    private String phone_number;
}
