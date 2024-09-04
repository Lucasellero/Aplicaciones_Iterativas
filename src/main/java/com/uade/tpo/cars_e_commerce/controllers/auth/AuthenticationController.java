package com.uade.tpo.cars_e_commerce.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.cars_e_commerce.controllers.config.JwtService;
import com.uade.tpo.cars_e_commerce.service.AuthenticationService;
import com.uade.tpo.cars_e_commerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    
    @GetMapping("/users")
    public ResponseEntity<UserDetails> getUserDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String autHeader) {
        String token = extractToken(autHeader);
        if (token != null && jwtService.isTokenValid(token, userDetailsService.loadUserByUsername(jwtService.extractUsername(token)))) {
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok(userDetailsService.loadUserByUsername(username));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            return authHeader.substring(7);
        }
        return null;
    }

}
