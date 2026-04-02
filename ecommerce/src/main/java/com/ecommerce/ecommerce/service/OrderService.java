package com.ecommerce.ecommerce.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.entity.Cart;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.repository.UserRepository;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private UserRepository userRepo;
    
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    
    public List<Order> getOrdersByUser(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepo.findByUser(user);
    }

    public Order placeOrder(Long userId, String address) {

        // 1. Get User
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get Cart Items
        List<Cart> cartItems = cartRepo.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 3. Get Products from Cart
        List<Product> products = cartItems
                .stream()
                .map(Cart::getProduct)
                .toList();

        // 4. Calculate Total Price
        double totalPrice = cartItems
                .stream()
                .mapToDouble(item ->
                        item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // 5. Create Order
        Order order = new Order();

        order.setUser(user);
        order.setProducts(products);
        order.setTotalPrice(totalPrice);
        order.setShippingAddress(address);
        order.setPaymentStatus("PENDING");

        // 6. Clear Cart
        cartRepo.deleteAll(cartItems);

        // 7. Save Order
        return orderRepo.save(order);
    }
}