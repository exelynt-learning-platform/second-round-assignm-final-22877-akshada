package com.ecommerce_backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteByProductId(Long productId);
    Optional<CartItem> findByIdAndCart(Long id, Cart cart);
}