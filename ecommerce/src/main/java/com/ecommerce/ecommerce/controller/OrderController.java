package com.ecommerce.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.entity.Order;

import com.ecommerce.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/{userId}")
    public Order placeOrder(
            @PathVariable Long userId,
            @RequestParam String address) {
        return service.placeOrder(userId, address);
    }
    
    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    // Get Orders by User
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return service.getOrdersByUser(userId);
    }
}
