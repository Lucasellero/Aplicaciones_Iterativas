package com.uade.tpo.cars_e_commerce.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.controllers.auth.UserRequest;
import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.exceptions.UserDuplicateException;
import com.uade.tpo.cars_e_commerce.exceptions.UserWrongPasswordException;
import com.uade.tpo.cars_e_commerce.service.UserService;


@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
   public ResponseEntity<Object> registerUser(@RequestBody UserRequest userRequest) throws UserDuplicateException {
    if (userService.findByUsername(userRequest.getUsername()) != null) {
        throw new UserDuplicateException();
    }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setPhone_number(userRequest.getPhone_number());
        user.setHome_address(userRequest.getHome_address());
        User result = userService.registerUser(user);
        return ResponseEntity.created(URI.create("/users/" + result.getId())).body(result);
       
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserRequest userRequest) throws UserWrongPasswordException, UserDuplicateException {
        if (userService.loginUser(userRequest.getUsername(), userRequest.getPassword())) {
            return ResponseEntity.ok().build();
        } else if (userService.findByUsername(userRequest.getUsername()) == null) {
            throw new UserDuplicateException();
        } else {
            throw new UserWrongPasswordException();
        }
    }

    @GetMapping("/get-user-by-username")
    public ResponseEntity<User> getUser(@RequestParam String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    
}