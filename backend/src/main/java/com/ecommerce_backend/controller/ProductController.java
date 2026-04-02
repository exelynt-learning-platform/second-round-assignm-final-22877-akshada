package com.ecommerce_backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.service.ProductService;

import jakarta.transaction.Transactional;

import com.ecommerce_backend.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repo;
    private final ProductService service;

    public ProductController(ProductRepository repo, ProductService service){
        this.repo = repo;
        this.service = service;
    }

    @GetMapping
    public List<Product> getAll(){
        return repo.findAll();
    }

    @PostMapping
    public Product create(@RequestBody Product p){
    	p.setId(null); 
        return repo.save(p);
    }

    // ✅ DELETE using service
    @Transactional
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }
    
    @Transactional
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }
}