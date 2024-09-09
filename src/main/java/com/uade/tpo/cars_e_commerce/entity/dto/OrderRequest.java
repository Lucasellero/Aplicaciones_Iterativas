package com.uade.tpo.cars_e_commerce.entity.dto;

import java.sql.Timestamp;
import java.util.List;

public class OrderRequest {
    private Long id;
    private String status;
    private Timestamp createdAt;
    private Double total;
    private UserRequest user;
    private List<CarritoItemRequest> items;

}
