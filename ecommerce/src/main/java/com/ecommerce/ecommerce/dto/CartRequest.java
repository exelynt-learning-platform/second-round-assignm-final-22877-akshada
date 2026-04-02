package com.ecommerce.ecommerce.dto;



public class CartRequest {

    private Long userId;
    private Long productId;
    private int quantity;

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}