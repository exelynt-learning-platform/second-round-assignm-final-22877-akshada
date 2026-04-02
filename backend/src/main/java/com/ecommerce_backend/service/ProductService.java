package com.ecommerce_backend.service;

import org.springframework.stereotype.Service;

import com.ecommerce_backend.repository.ProductRepository;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.repository.CartItemRepository;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final CartItemRepository cartItemRepo;

    public ProductService(ProductRepository productRepo, CartItemRepository cartItemRepo) {
        this.productRepo = productRepo;
        this.cartItemRepo = cartItemRepo;
    }

    public String deleteProduct(Long id) {

        // 🔥 Step 1: delete cart items referencing this product
        cartItemRepo.deleteByProductId(id);

        // 🔥 Step 2: delete product
        productRepo.deleteById(id);

        return "Product deleted successfully";
    }
    
    public Product updateProduct(Long id, Product updatedProduct) {
    	if (updatedProduct.getPrice() <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }

        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ Update fields
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStock(updatedProduct.getStock());
        existing.setImageUrl(updatedProduct.getImageUrl());

        return productRepo.save(existing);
    }
}