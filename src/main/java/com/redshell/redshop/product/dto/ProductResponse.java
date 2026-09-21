package com.redshell.redshop.product.dto;

import com.redshell.redshop.product.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category
) {

    public static ProductResponse from(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null
                        ? product.getCategory().getName()
                        : null
        );
    }
}