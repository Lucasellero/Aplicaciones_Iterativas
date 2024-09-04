package com.uade.tpo.cars_e_commerce.entity.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {
    private Long cartId;
    private Long productId;
    private Long quantity;
    
}
