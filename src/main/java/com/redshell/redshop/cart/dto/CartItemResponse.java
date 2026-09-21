package com.redshell.redshop.cart.dto;

import com.redshell.redshop.cart.CartItem;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Long productId,
        String productName,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal subtotal
) {

    public static CartItemResponse from(CartItem item) {

        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}