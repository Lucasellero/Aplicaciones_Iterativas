package com.uade.tpo.cars_e_commerce.service;

import java.util.List;

import com.uade.tpo.cars_e_commerce.entity.CarritoItem;
public interface CarritoItemService {

    CarritoItem save(CarritoItem carritoItem);

    void delete(Long id);

    List<CarritoItem> findByCarritoId(Long carritoId);

    

}
