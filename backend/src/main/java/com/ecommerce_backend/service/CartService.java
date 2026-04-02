package com.ecommerce_backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.CartItemRepository;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.repository.ProductRepository;
import com.ecommerce_backend.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public CartService(CartRepository cartRepo, CartItemRepository itemRepo,
                       ProductRepository productRepo, UserRepository userRepo){
        this.cartRepo = cartRepo;
        this.itemRepo = itemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    private User getUserFromToken(String token){
        // For testing, just fetch user with ID=1
        return userRepo.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public String addToCart(Long productId, int quantity){
        User user = userRepo.findById(1L).orElseThrow(() -> new RuntimeException("User not found")); // replace with JWT
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepo.findByUser(user).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            return cartRepo.save(c);
        });

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if(existingItem.isPresent()){
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        cartRepo.save(cart);
        return "Product added to cart";
    }

    // Get cart
    public Cart getCartByUser(String token){
        User user = getUserFromToken(token);
        return cartRepo.findByUser(user).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            return cartRepo.save(c);
        });
    }

    // Update cart item
    public String updateCartItem(Long cartItemId, int quantity, String token){
        User user = getUserFromToken(token);
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem item = itemRepo.findByIdAndCart(cartItemId, cart)
                .orElseThrow(() -> new RuntimeException("Cart item not found or not yours"));

        item.setQuantity(quantity);
        itemRepo.save(item);
        return "Cart item updated";
    }

    // Remove cart item
    public String removeCartItem(Long cartItemId, String token){
        User user = getUserFromToken(token);
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem item = itemRepo.findByIdAndCart(cartItemId, cart)
                .orElseThrow(() -> new RuntimeException("Cart item not found or not yours"));

        cart.getItems().remove(item);
        itemRepo.delete(item);
        return "Cart item removed";
    }
}