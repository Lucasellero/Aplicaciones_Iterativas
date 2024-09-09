package com.uade.tpo.cars_e_commerce.controllers;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.cars_e_commerce.entity.Car;
import com.uade.tpo.cars_e_commerce.entity.Image;
import com.uade.tpo.cars_e_commerce.service.CarService;
import com.uade.tpo.cars_e_commerce.service.ImageService;
@RestController
@RequestMapping("images")
public class ImagesController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private CarService carService;



   @CrossOrigin
@GetMapping("/display")
public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException {
    Image image = imageService.viewById(id);
    byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
    
    // Establece el tipo de contenido según el formato de la imagen
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG); // Cambia a MediaType.IMAGE_PNG si es una imagen PNG
    
    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
}

    /*@PostMapping()
    public String addImagePost(AddFileRequest request) throws IOException, SerialException, SQLException {
        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.create(Image.builder().image(blob).build());
        return "created";
    }
        */
     
    @PostMapping("/add") 
public String addImagePost(
        @RequestParam("file") MultipartFile file,
        @RequestParam("carId") long carId) throws IOException, SQLException {
    byte[] bytes = file.getBytes();
    Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
    
    Car car = carService.getCarById(carId).get();
   
    Image image = Image.builder()
            .image(blob)
            .car(car)
            .build();
    
    imageService.create(image);
    return "created";
}


}