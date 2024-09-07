package com.uade.tpo.cars_e_commerce.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.CarritoItem;
import com.uade.tpo.cars_e_commerce.repository.CarritoItemRepository;

@Service
public class CarritoItemServiceImpl implements CarritoItemService {

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Override
    public CarritoItem save(CarritoItem carritoItem) {
        return carritoItemRepository.save(carritoItem);
    }

    @Override
    public void delete(Long id) {
        carritoItemRepository.deleteById(id);
    }

    @Override
    public List<CarritoItem> findByCarritoId(Long carritoId) {
        return carritoItemRepository.findByCarrito_CarritoId(carritoId);
    }
}
