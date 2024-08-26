package com.uade.tpo.cars_e_commerce.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.entity.dto.UserRequest;
import com.uade.tpo.cars_e_commerce.exceptions.UserDuplicateException;
import com.uade.tpo.cars_e_commerce.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> createCategory(@RequestBody UserRequest userRequest) 
        throws UserDuplicateException {
            User result = userService.registerUser(userRequest.getUsername(), userRequest.getPassword());
            return ResponseEntity.created(URI.create("/users/" + result.getId())).body(result);
    }

    @PostMapping("/login")
    public boolean loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }
}