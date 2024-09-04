package com.uade.tpo.cars_e_commerce.entity;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShopCart {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long shopCartid;

        @Column
        private Double totalAmount = Double.valueOf(0);

        private String status;

        @OneToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column (name = "user_id", insertable = false, updatable = false)
        private Long userId;

        @OneToMany(mappedBy = "shopCart", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<ShopCartLine> shopCartLine;

        public void addItem(ShopCartLine shopCartLine) {
                this.shopCartLine.add(shopCartLine);
                shopCartLine.setShopCart(this);
                updateTotalAmount();
        }


        public void removeItem(ShopCartLine shopCartLine) {
                this.shopCartLine.remove(shopCartLine);
                shopCartLine.setShopCart(null);
                updateTotalAmount();
        }

        public void updateTotalAmount() {
                this.totalAmount = shopCartLine.stream().map( item -> {
                        Double unitPrice = item.getUnitPrice();
                        if (unitPrice == null) {
                                return 0.0;
                        }
                        return unitPrice * item.getQuantity();
                }).reduce(0.0, (a, b) -> a + b);
        }
}