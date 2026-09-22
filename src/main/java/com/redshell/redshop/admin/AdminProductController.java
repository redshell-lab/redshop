package com.redshell.redshop.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminProductController {

    private final ProductRepository productRepository;

    public AdminProductController(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @GetMapping("/admin/products")
    public String products(Model model) {

        model.addAttribute(
                "products",
                productRepository.findAll()
        );

        return "admin/products/list";
    }
}