package com.uade.tpo.cars_e_commerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "carrito_item")
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)  
    private Car car;

    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    @JsonBackReference
    private Carrito carrito;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "quantity")
    private Long quantity;

    public Double getSubtotal() {
        Double price = car.getPrice();
        Double discount = car.getDiscount() != null ? car.getDiscount() : 0.0;
        Double discountedPrice = price - discount;
        if (discountedPrice < 0) discountedPrice = 0.0;
        return discountedPrice * quantity;
    }

}