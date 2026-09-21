package com.redshell.redshop.order.dto;

import com.redshell.redshop.order.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String status,
        BigDecimal total,
        LocalDateTime createdAt,
        String fullName,
        String street,
        String city,
        String postalCode,
        String phone,
        List<OrderItemResponse> items
) {

    public static OrderResponse from(Order order) {

        List<OrderItemResponse> items =
                order.getItems()
                        .stream()
                        .map(OrderItemResponse::from)
                        .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotal(),
                order.getCreatedAt(),
                order.getAddress().getFullName(),
                order.getAddress().getStreet(),
                order.getAddress().getCity(),
                order.getAddress().getPostalCode(),
                order.getAddress().getPhone(),
                items
        );
    }
}