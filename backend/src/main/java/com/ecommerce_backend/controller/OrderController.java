package com.ecommerce_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ecommerce_backend.dto.OrderRequest;
import com.ecommerce_backend.entity.Order;
import com.ecommerce_backend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // ✅ Create order from cart
    @PostMapping("/create-from-cart")
    public Order createOrderFromCart(@RequestHeader("Authorization") String token,
                                     @RequestBody OrderRequest request) throws Exception {
        return orderService.createOrderFromCart(token, request.getShippingAddress());
    }

    // ✅ Get all orders of logged-in user
    @GetMapping
    public List<Order> getOrders(@RequestHeader("Authorization") String token) {
        return orderService.getOrdersForUser(token);
    }
}