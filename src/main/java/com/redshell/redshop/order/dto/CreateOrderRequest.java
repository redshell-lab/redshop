package com.redshell.redshop.order.dto;

public record CreateOrderRequest(
        String fullName,
        String street,
        String city,
        String postalCode,
        String phone
) {
}