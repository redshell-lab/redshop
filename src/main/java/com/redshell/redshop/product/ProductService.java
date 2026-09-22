package com.redshell.redshop.product;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public Product create(
            String name,
            String description,
            BigDecimal price,
            Integer stock,
            Category category
    ) {
        Product product = new Product(
                name,
                description,
                price,
                stock,
                category
        );

        return productRepository.save(product);
    }
}