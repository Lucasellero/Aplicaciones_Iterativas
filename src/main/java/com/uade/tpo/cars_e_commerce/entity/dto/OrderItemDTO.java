package com.uade.tpo.cars_e_commerce.entity.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Long carId;
    private String carModel;
    private Double carPrice;
    private Long quantity;
    private Double total;
    
}
