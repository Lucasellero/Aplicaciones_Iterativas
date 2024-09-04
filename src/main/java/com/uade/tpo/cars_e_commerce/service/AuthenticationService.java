package com.uade.tpo.cars_e_commerce.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.cars_e_commerce.controllers.auth.AuthenticationRequest;
import com.uade.tpo.cars_e_commerce.controllers.auth.AuthenticationResponse;
import com.uade.tpo.cars_e_commerce.controllers.auth.UserRequest;
import com.uade.tpo.cars_e_commerce.controllers.config.JwtService;
import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class AuthenticationService {
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        
        
      public AuthenticationResponse register(UserRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent() ||
        repository.findByUsername(request.getUsername()).isPresent()) {
        throw new IllegalArgumentException("El correo electrónico o nombre de usuario ya existe");
    }
                var user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .name(request.getName())
                                .surname(request.getSurname())
                                .phone_number(request.getPhone_number())
                                .home_address(request.getHome_address())
                                .role(request.getRole()) 
                                .username(request.getUsername())
                                .build();
                repository.save(user);
                var jwtToken = jwtService.generateToken( user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build(); 
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));

                var user = repository.findByUsername(request.getUsername())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }
        
         public User findUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        }
}

