package com.ecommerce_backend.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="orders")
@Data
public class Order {

 @Id @GeneratedValue
 private Long id;

 @ManyToOne
 private User user;

 @ManyToMany
 private List<Product> products;

 private double totalPrice;
 private String status;
 private String shippingAddress;
}
