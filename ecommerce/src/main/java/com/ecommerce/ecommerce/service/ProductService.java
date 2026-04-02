package com.ecommerce.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.exception.BadRequestException;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product getProduct(Long id) {

        return repo.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));
    }

    public Product add(Product p) {

        if (p.getPrice() <= 0) {
            throw new BadRequestException("Price must be greater than 0");
        }

        return repo.save(p);
    }

    public void delete(Long id) {

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }

        repo.deleteById(id);
    }
    
    public List<Product> getAll() {

        List<Product> products = repo.findAll();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products available");
        }

        return products;
    }
}