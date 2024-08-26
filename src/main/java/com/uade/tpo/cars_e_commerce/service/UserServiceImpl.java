package com.uade.tpo.cars_e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String username, String password) {
        // Implementa la lógica para registrar un usuario
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        return userRepository.save(newUser);
    }

    @Override
    public boolean loginUser(String username, String password) {
        // Implementa la lógica para el login de usuario
        User user = userRepository.findByUsernameCustomQuery(username);
        return user != null && user.getPassword().equals(password);
    }
}
