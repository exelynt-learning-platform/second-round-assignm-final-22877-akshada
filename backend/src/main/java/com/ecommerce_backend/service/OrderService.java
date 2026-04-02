package com.ecommerce_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.entity.Order;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.repository.OrderRepository;
import com.ecommerce_backend.security.JwtUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final JwtUtil jwt;

    public OrderService(OrderRepository orderRepo, CartRepository cartRepo, JwtUtil jwt){
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.jwt = jwt;
    }

    @Transactional
    public Order createOrderFromCart(String token, String shippingAddress) throws Exception {
        // 1️⃣ Get user email from JWT
        String email = jwt.extractUsername(token.substring(7)); // remove "Bearer "

        // 2️⃣ Get cart for this user
        Cart cart = cartRepo.findAll().stream()
                .filter(c -> c.getUser().getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }

        // 3️⃣ Calculate total price
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // 4️⃣ Create order
        Order order = new Order();
        order.setUser(cart.getUser());
        List<Product> products = cart.getItems().stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toList());
        order.setProducts(products);
        order.setTotalPrice(totalPrice);
        order.setShippingAddress(shippingAddress);
        order.setStatus("Pending");

        // 5️⃣ Clear the cart
        cart.getItems().clear();
        cartRepo.save(cart);

        // 6️⃣ Save order
        return orderRepo.save(order);
    }

    public List<Order> getOrdersForUser(String token){
        String email = jwt.extractUsername(token.substring(7));
        return orderRepo.findAll().stream()
                .filter(o -> o.getUser().getEmail().equals(email))
                .collect(Collectors.toList());
    }
}