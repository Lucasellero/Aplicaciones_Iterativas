package com.uade.tpo.cars_e_commerce.service;


import java.util.Optional;

import com.uade.tpo.cars_e_commerce.entity.Image;

import org.springframework.stereotype.Service;



@Service
public interface ImageService {
    Image create(Image image);
    Image viewById(long id);
    Image findByCarId(Long carId);
    Image getImageByCarId(Long carId);
}
