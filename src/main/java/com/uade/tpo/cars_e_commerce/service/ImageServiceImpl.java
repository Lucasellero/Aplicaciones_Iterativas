package com.uade.tpo.cars_e_commerce.service;

import com.uade.tpo.cars_e_commerce.entity.Cars;
import com.uade.tpo.cars_e_commerce.entity.Image;
import com.uade.tpo.cars_e_commerce.exceptions.ResourceNotFoundException;
import com.uade.tpo.cars_e_commerce.repository.ImageRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CarService carService;

    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }

      @Override
    public Image viewById(long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            return optionalImage.get();
        } else {
            throw new ResourceNotFoundException("Image not found with id " + id);
        }
    }
    @Override
    public Image findByCarId(Long carId) {
        // Obtén el objeto Car usando carId
        Cars car = carService.getCarById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id " + carId));

        // Busca la imagen asociada al coche y lanza una excepción si no se encuentra
        return imageRepository.findByCar(car)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found for car id " + carId));
    }

    @Override
    public Image getImageByCarId(Long carId) {
    Cars car = carService.getCarById(carId)
            .orElseThrow(() -> new ResourceNotFoundException("Car not found with id " + carId));

    return imageRepository.findByCar(car)
            .orElseThrow(() -> new ResourceNotFoundException("Image not found for car id " + carId));
}

}
