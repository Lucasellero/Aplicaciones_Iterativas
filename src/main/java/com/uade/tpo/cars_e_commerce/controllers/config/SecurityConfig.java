package com.uade.tpo.cars_e_commerce.controllers.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uade.tpo.cars_e_commerce.entity.Role;

import lombok.RequiredArgsConstructor;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                            .csrf(AbstractHttpConfigurer::disable)
                            .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                                            .requestMatchers("/api/v1/auth/register").permitAll()
                                            .requestMatchers("/api/v1/auth/login").permitAll()
                                            .requestMatchers("/error/**").permitAll()
                                            .requestMatchers("/user/**").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/cars/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                            .requestMatchers("/cars/{id}").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/cars/create").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/cart/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                            .requestMatchers("/orders/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                            .requestMatchers("/cartItems/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                            .requestMatchers("/cartItems/item/add").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                            .requestMatchers("/carrito/**").hasAnyAuthority(Role.ADMIN.name(),Role.USER.name())
                                            .anyRequest()
                                            .authenticated())
                            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                            .authenticationProvider(authenticationProvider)
                            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }
}
