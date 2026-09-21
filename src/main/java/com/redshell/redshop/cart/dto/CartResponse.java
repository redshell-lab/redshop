package com.redshell.redshop.cart.dto;

import com.redshell.redshop.cart.Cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        Long id,
        List<CartItemResponse> items,
        BigDecimal total
) {

    public static CartResponse from(Cart cart) {

        List<CartItemResponse> items =
                cart.getItems()
                        .stream()
                        .map(CartItemResponse::from)
                        .toList();

        return new CartResponse(
                cart.getId(),
                items,
                cart.getTotal()
        );
    }
}