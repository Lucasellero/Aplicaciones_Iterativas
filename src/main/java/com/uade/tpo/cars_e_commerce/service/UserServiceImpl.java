package com.uade.tpo.cars_e_commerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
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

     
}