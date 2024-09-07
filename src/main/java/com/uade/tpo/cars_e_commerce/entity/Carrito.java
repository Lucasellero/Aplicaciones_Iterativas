package com.uade.tpo.cars_e_commerce.entity;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "carrito")
public class Carrito {
 
    @OneToOne 
    @JoinColumn(name = "usuario_id")
    @JsonIgnore // Evita que se genere un loop infinito al serializar el objeto
    private User user;

    @Id
    @Column(name = "id")
    private Long carritoId;

    @ElementCollection
    @Column(name = "items")
    private Map<Car, Long> items;
    
    @Column(name = "total")
    private Double total;

}