package com.ecommerce_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CartItem {
 @Id @GeneratedValue
 private Long id;

 @ManyToOne
 private Product product;

 @ManyToOne
 @JoinColumn(name = "cart_id")
 private Cart cart;
 
 private int quantity;
}
