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
import com.uade.tpo.cars_e_commerce.entity.Carrito;
import com.uade.tpo.cars_e_commerce.entity.User;
import com.uade.tpo.cars_e_commerce.repository.UserRepository;
import com.uade.tpo.cars_e_commerce.repository.CarritoRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class AuthenticationService {
        private final UserRepository UserRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final CarritoRepository CarritoRepository;
        
      public AuthenticationResponse register(UserRequest request) {
        if (UserRepository.findByEmail(request.getEmail()).isPresent() ||
        UserRepository.findByUsername(request.getUsername()).isPresent()) {
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
                                
                var cart = Carrito.builder()
                                .user(user)
                                .build();
                UserRepository.save(user);
                CarritoRepository.save(cart);
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

                var user = UserRepository.findByUsername(request.getUsername())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }
        
         public User findUserByUsername(String username) {
        return UserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        }
}

