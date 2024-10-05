package com.uade.tpo.cars_e_commerce.entity.dto;


import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private String status;
    private Timestamp createdAt;
    private Double total;
    private OrderUserDTO user;
    private List<OrderItemDTO> items;
    
    
}
