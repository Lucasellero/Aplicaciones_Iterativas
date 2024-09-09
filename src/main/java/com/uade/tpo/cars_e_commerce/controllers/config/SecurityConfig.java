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
                            .authorizeHttpRequests(req -> req
                                             //cartItems, orders es lo que faltaria y capaz se usa.
                                            .requestMatchers("/api/v1/auth/**").permitAll()
                                            .requestMatchers("/api/v1/auth/register").permitAll()
                                            .requestMatchers("/api/v1/auth/login").permitAll()
                                            .requestMatchers("/api/v1/auth/users").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/users/get-user-by-username").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/users/get-all-users").hasAnyAuthority(Role.ADMIN.name())

                                            .requestMatchers("/car/create").hasAnyAuthority(Role.ADMIN.name()) 
                                            .requestMatchers("/car/delete").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/car/manufacturer").permitAll()
                                            .requestMatchers("/car/price").permitAll()
                                            .requestMatchers("/car/color").permitAll()
                                            .requestMatchers("/car/model").permitAll()
                                            .requestMatchers("/car/year").permitAll()
                                            .requestMatchers("/car/update/**").hasAnyAuthority(Role.ADMIN.name()) 

                                            .requestMatchers("/image/**").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/carrito/**").hasAnyAuthority(Role.USER.name())

                                            .requestMatchers("/order/**").hasAnyAuthority(Role.ADMIN.name())

                                            .requestMatchers("/error/**").permitAll()

                                            .anyRequest()
                                            .authenticated())
                            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                            .authenticationProvider(authenticationProvider)
                            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }
}
