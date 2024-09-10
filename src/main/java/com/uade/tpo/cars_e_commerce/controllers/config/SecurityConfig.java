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
                                             //cartItems, orders es lo que faltaria y capaz se usa
                                            .requestMatchers("/api/v1/auth/register").permitAll()
                                            .requestMatchers("/api/v1/auth/login").permitAll()
                                            .requestMatchers("/api/v1/auth/users").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/users/get-user-by-username").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/users/get-all-users").hasAnyAuthority(Role.ADMIN.name())
                                            
                                            .requestMatchers("/car/create").hasAnyAuthority(Role.ADMIN.name()) 
                                            .requestMatchers("/car/delete").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/car//{carId}").permitAll()
                                            .requestMatchers("/car/all").permitAll()
                                            .requestMatchers("/car/manufacturer/{manufacturer}").permitAll()
                                            .requestMatchers("/car/price/{price}").permitAll()
                                            .requestMatchers("/car/color/{color}").permitAll()
                                            .requestMatchers("/car/model/{model_name}").permitAll()
                                            .requestMatchers("/car/year/{model_year}").permitAll()
                                            .requestMatchers("/car/{carId}/update/manufacture/{manufacturer}").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/car/{carId}/update/color/{color}").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/car/{carId}/update/modelYear/{model_year}").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/car/{carId}/update/modelName/{model_name}").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/car/{carId}/update/price/{price}").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/car/{carId}/update/stock/{stock}").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/car/{carId}/update/set-discount/{discount}").hasAnyAuthority(Role.ADMIN.name())

                                            .requestMatchers("/image/display").permitAll()
                                            .requestMatchers("/image/add").hasAnyAuthority(Role.ADMIN.name())
                                            
                                            .requestMatchers("/carrito/**").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/{cartId}/my-cart").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/{cartId}/clear-cart").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/{cartId}/total-price").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/{cartId}/add-product/{productId}").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/{cartId}/delete-product/{productId}").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/{cartId}/update-product/{productId}/{quantity}").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/{cartId}/decrease-product/{productId}").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/{cartId}/increase-product/{productId}").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/checkout-from-cart/{cartId}").hasAnyAuthority(Role.USER.name())

                                            .requestMatchers("/order/all").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/order/id/{orderId}").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/order/user/id/{userId}").hasAnyAuthority(Role.USER.name())

                                            .requestMatchers("/error/**").permitAll()

                                            .anyRequest()
                                            .authenticated())
                            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                            .authenticationProvider(authenticationProvider)
                            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }
}
