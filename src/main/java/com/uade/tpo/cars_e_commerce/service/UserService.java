package com.uade.tpo.cars_e_commerce.service;

import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.exceptions.UserDuplicateException;



public interface UserService {
    public User registerUser(User user) throws UserDuplicateException;
    public boolean loginUser(String username, String password);
    public User findByEmail(String email);
    public Iterable<User> findAll();
    public User findByUsername(String username);
}