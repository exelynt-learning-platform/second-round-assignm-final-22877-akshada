package com.ecommerce_backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    // Find cart for a given user
    Optional<Cart> findByUser(User user);
    
}