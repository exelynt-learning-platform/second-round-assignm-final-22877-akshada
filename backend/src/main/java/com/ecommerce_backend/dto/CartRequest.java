package com.ecommerce_backend.dto;


import lombok.Data;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;

@Data
public class CartRequest {
//	 @NotNull(message = "productId is required")
    private Long productId;
//	 @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;
}
