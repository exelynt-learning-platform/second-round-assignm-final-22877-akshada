package com.ecommerce.ecommerce.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.dto.CartRequest;
import com.ecommerce.ecommerce.entity.Cart;
import com.ecommerce.ecommerce.service.CartService;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping
    public Cart addToCart(@RequestBody CartRequest request) {
        return service.addToCart(request);
    }
}