package com.uade.tpo.cars_e_commerce.controllers;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddFileRequest {
    private String name;
    private MultipartFile file;
    
}
