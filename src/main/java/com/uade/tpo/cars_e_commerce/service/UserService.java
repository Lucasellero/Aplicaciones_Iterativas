package com.uade.tpo.cars_e_commerce.service;

import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.exceptions.UserDuplicateException;



public interface UserService {
    public User registerUser(String username, String password) throws UserDuplicateException;
    public boolean loginUser(String username, String password);


    
}
