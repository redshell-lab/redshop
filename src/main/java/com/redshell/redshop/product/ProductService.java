package com.redshell.redshop.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private static final Logger log =
            LoggerFactory.getLogger(ProductService.class);

    private static final Logger auditLog =
            LoggerFactory.getLogger("AUDIT_EVENT");

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {

        log.info("Fetching all products");

        return productRepository.findAll();
    }

    public List<Product> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {

            log.info("Product search requested without keyword");

            return productRepository.findAll();
        }

        log.info("Searching products with keyword='{}'", keyword);

        return productRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }

    public Product findById(Long id) {

        log.info("Fetching product with id={}", id);

        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with id={}", id);
                    return new IllegalArgumentException("Product not found");
                });
    }

    public Product create(
            String name,
            String description,
            BigDecimal price,
            Integer stock,
            Category category
    ) {

        log.info(
                "Creating product: name='{}', price={}, stock={}, categoryId={}",
                name,
                price,
                stock,
                category != null ? category.getId() : null
        );

        Product product = new Product(
                name,
                description,
                price,
                stock,
                category
        );

        Product savedProduct = productRepository.save(product);

        auditLog.info(
                "PRODUCT_CREATED user={} productId={}",
                currentUsername(),
                savedProduct.getId()
        );

        return savedProduct;
    }

    public Product update(
            Long id,
            String name,
            String description,
            BigDecimal price,
            Integer stock,
            Category category
    ) {

        log.info(
                "Updating product: id={}, name='{}', price={}, stock={}, categoryId={}",
                id,
                name,
                price,
                stock,
                category != null ? category.getId() : null
        );

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found for update with id={}", id);
                    return new IllegalArgumentException("Product not found");
                });

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        auditLog.info(
                "PRODUCT_UPDATED user={} productId={}",
                currentUsername(),
                updatedProduct.getId()
        );

        return updatedProduct;
    }

    public void delete(Long id) {

        log.info("Deleting product with id={}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found for deletion with id={}", id);
                    return new IllegalArgumentException("Product not found");
                });

        productRepository.delete(product);

        auditLog.info(
                "PRODUCT_DELETED user={} productId={}",
                currentUsername(),
                id
        );
    }

    private String currentUsername() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null) {
            return "anonymous";
        }

        return authentication.getName();
    }
}