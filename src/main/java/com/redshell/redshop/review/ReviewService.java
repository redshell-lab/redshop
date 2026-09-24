package com.redshell.redshop.review;

import com.redshell.redshop.product.Product;
import com.redshell.redshop.product.ProductService;
import com.redshell.redshop.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    public ReviewService(
            ReviewRepository reviewRepository,
            ProductService productService
    ) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    public List<Review> findByProductId(Long productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }

    public Review create(
            Long productId,
            String content,
            User user
    ) {
        Product product = productService.findById(productId);

        Review review = new Review(
                content,
                LocalDateTime.now(),
                user,
                product
        );

        return reviewRepository.save(review);
    }
}