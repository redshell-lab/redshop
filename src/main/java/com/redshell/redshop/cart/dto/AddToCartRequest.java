package com.redshell.redshop.cart.dto;

public record AddToCartRequest(
        Long productId,
        Integer quantity
) {
}