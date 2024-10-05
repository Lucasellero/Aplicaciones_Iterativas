package com.uade.tpo.cars_e_commerce.service;




import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.Image;



@Service
public interface ImageService {
    Image create(Image image);
    Image viewById(long id);
    Image findByCarId(Long carId);
    Image getImageByCarId(Long carId);
}
