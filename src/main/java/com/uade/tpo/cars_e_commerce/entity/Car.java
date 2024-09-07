package com.uade.tpo.cars_e_commerce.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "Car")
public class Car{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long carId;
    
        @Column
        private String manufacturer;
    
        @Column(name = "model_name")
        private String modelName;
    
        @Column(name = "model_year")
        private Integer modelYear;
    
        @Column(name = "color")
        private String color;
    
        @Column(name = "price")
        private Double price;
    
        @Column
        private Integer stock;
        
       @OneToOne(mappedBy = "car")
       @JsonIgnore // Para probar despues
        private Image image;

       /*  @OneToMany(mappedBy = "car")
        private Set<Carrito> carritos;*/
    }