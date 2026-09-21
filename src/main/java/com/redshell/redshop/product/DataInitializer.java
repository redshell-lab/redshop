package com.redshell.redshop.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            CategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        return args -> {

            if (categoryRepository.count() > 0) {
                return;
            }

            Category laptops = new Category("Laptops");
            Category accessories = new Category("Accessories");
            Category monitors = new Category("Monitors");

            categoryRepository.save(laptops);
            categoryRepository.save(accessories);
            categoryRepository.save(monitors);

            productRepository.save(
                    new Product(
                            "ThinkPad X1",
                            "Business laptop with Intel processor.",
                            new BigDecimal("1299.99"),
                            5,
                            laptops
                    )
            );

            productRepository.save(
                    new Product(
                            "Mechanical Keyboard",
                            "Mechanical keyboard with RGB lighting.",
                            new BigDecimal("89.99"),
                            20,
                            accessories
                    )
            );

            productRepository.save(
                    new Product(
                            "27-inch Monitor",
                            "27-inch IPS monitor with 144Hz refresh rate.",
                            new BigDecimal("349.99"),
                            8,
                            monitors
                    )
            );

            productRepository.save(
                    new Product(
                            "Wireless Mouse",
                            "Wireless mouse with programmable buttons.",
                            new BigDecimal("49.99"),
                            30,
                            accessories
                    )
            );
        };
    }
}