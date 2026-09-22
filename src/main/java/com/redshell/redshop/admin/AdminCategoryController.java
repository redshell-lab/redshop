package com.redshell.redshop.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminCategoryController {

    private final CategoryRepository categoryRepository;

    public AdminCategoryController(
            CategoryRepository categoryRepository
    ) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin/categories")
    public String categories(Model model) {

        model.addAttribute(
                "categories",
                categoryRepository.findAll()
        );

        return "admin/categories/list";
    }
}