package com.ecommerce_backend.entity;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
 @Id @GeneratedValue
 private Long id;

 private String email;
 private String password;
 private String role;

 @OneToMany(mappedBy="user")
 private List<Order> orders;
}
