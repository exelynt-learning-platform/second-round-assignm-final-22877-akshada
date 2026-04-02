package com.ecommerce_backend.controller;
import org.springframework.web.bind.annotation.*;
import com.ecommerce_backend.dto.CartRequest;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.service.CartService;

@RestController
@RequestMapping("/cart")   
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    // ✅ ADD TO CART
    @PostMapping("/add")
    public String addToCart(@RequestBody CartRequest request) {
        return service.addToCart(request.getProductId(), request.getQuantity());
    }
    
    @GetMapping
    public Cart getCart(@RequestHeader("Authorization") String token){
        return service.getCartByUser(token);
    }

    @PutMapping("/update/{cartItemId}")
    public String updateCart(@PathVariable Long cartItemId,
                             @RequestBody CartRequest request,
                             @RequestHeader("Authorization") String token){
        return service.updateCartItem(cartItemId, request.getQuantity(), token);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public String removeCartItem(@PathVariable Long cartItemId,
                                 @RequestHeader("Authorization") String token){
        return service.removeCartItem(cartItemId, token);
    }
}
