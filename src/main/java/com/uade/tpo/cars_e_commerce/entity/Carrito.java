package com.uade.tpo.cars_e_commerce.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

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

    //@ElementCollection
    //@Column(name = "items")
    //private Map<Car, Long> items;

   
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CarritoItem> items = new ArrayList<>();
    
    @Column(name = "total")
    private Double total;

}