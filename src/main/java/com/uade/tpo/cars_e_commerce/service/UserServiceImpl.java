package com.uade.tpo.cars_e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);//agregar excepcion
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean loginUser(String username, String password) {
       Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return user.get().getPassword().equals(password);
        }
        return false;
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null); //agregar excepcion
    }
}