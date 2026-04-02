package com.ecommerce_backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Cart {
 @Id @GeneratedValue
 private Long id;

 @OneToOne
 private User user;

 @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true )
 private List<CartItem> items = new ArrayList<>();
}
