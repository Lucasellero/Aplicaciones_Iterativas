package com.uade.tpo.cars_e_commerce.entity;
import java.util.Map;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "carrito")
public class Carrito {
 
    @OneToOne 
    @JoinColumn(name = "usuario_id")
    private User user;

    @Id
    @Column(name = "id")
    private Long carritoId = user.getId();

    @ElementCollection
    @Column(name = "items")
    private Map<Car, Long> items;
    
    @Column(name = "total")
    private Double total;

}