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

    public Product update(
            Long id,
            String name,
            String description,
            BigDecimal price,
            Integer stock,
            Category category
    ) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found")
                );

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);

        return productRepository.save(product);
    }


    public void delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found")
                );

        productRepository.delete(product);
    }
}