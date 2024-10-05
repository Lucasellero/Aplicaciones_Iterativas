package com.uade.tpo.cars_e_commerce.entity.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoDTO {
    private Long carritoId;
    private List<CarritoItemDTO> items;
    private Double total;
    
}
