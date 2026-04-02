package com.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce_backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
