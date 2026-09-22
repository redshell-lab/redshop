package com.redshell.redshop.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public AdminProductController(
            ProductRepository productRepository,
            ProductService productService,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin/products")
    public String products(Model model) {

        model.addAttribute(
                "products",
                productRepository.findAll()
        );

        return "admin/products/list";
    }

    @GetMapping("/admin/products/new")
    public String newProduct(Model model) {

        model.addAttribute(
                "categories",
                categoryRepository.findAll()
        );

        return "admin/products/form";
    }

    @PostMapping("/admin/products")
    public String createProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam java.math.BigDecimal price,
            @RequestParam Integer stock,
            @RequestParam Long categoryId
    ) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Category not found")
                );

        productService.create(
                name,
                description,
                price,
                stock,
                category
        );

        return "redirect:/admin/products";
    }
}