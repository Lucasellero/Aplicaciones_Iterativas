package com.uade.tpo.cars_e_commerce.entity;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class Cars{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long carId;
    
        @Column
        private String manufacturer;
    
        @Column(name = "model_name")
        private String modelName;
    
        @Column(name = "model_year")
        private int modelYear;
    
        @Column
        private String color;
    
        @Column
        private Double price;
    
        @Column
        private Integer stock;

        @ManyToOne
        @JoinColumn(name = "category_id", nullable = false)
        private Category category;
    
        @OneToMany(mappedBy = "car")
        private List<ShopCartLine> shopCartLines;
    
        @OneToMany(mappedBy = "car")
        private List<OrderLine> orderLines;
    }