package com.redshell.redshop.product;

import com.redshell.redshop.review.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductController(
            ProductService productService,
            ReviewService reviewService
    ) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) String search,
            Model model
    ) {
        model.addAttribute(
                "products",
                productService.search(search)
        );

        model.addAttribute("search", search);

        return "products/list";
    }

    @GetMapping("/products/{id}")
    public String product(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute(
                "product",
                productService.findById(id)
        );

        model.addAttribute(
                "reviews",
                reviewService.findByProductId(id)
        );

        return "products/detail";
    }
}