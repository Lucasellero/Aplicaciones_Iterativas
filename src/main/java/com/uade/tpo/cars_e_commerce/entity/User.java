package com.uade.tpo.cars_e_commerce.entity;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    public User(Long id , String email, String password, String name, String surname, String home_address, String phone_number, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.home_address = home_address;
        this.phone_number = phone_number;
        this.role = role;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String home_address;

    @Column
    private String phone_number;

    //poner relacion de uno a mucho con ordenCompra
    @OneToMany (mappedBy = "user")
    private List<Order> ordenCompra;

    @OneToOne (mappedBy = "user")
    private ShopCart carrito;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    private boolean isAccountNonExpired;

    @JsonIgnore
    private boolean isAccountNonLocked;

    @JsonIgnore
    private boolean isCredentialsNonExpired;

    @JsonIgnore
    private boolean isEnabled;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

}