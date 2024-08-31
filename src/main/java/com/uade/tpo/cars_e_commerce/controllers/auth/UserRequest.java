package com.uade.tpo.cars_e_commerce.controllers.auth;

import com.uade.tpo.cars_e_commerce.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String home_address;
    private String phone_number; //Cuidado con el tipo de dato
    private String username;
    private Role role;
}