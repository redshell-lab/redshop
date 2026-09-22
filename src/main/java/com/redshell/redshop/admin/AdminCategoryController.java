package com.redshell.redshop.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/admin/categories/new")
    public String newCategory() {

        return "admin/categories/form";
    }

    @PostMapping("/admin/categories")
    public String createCategory(
            @RequestParam String name
    ) {

        Category category = new Category(name);

        categoryRepository.save(category);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/{id}/edit")
    public String editCategory(
            @PathVariable Long id,
            Model model
    ) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Category not found")
                );

        model.addAttribute(
                "category",
                category
        );

        return "admin/categories/form";
    }

    @PostMapping("/admin/categories/{id}")
    public String updateCategory(
            @PathVariable Long id,
            @RequestParam String name
    ) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Category not found")
                );

        category.setName(name);

        categoryRepository.save(category);

        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories/{id}/delete")
    public String deleteCategory(
            @PathVariable Long id
    ) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Category not found")
                );

        categoryRepository.delete(category);

        return "redirect:/admin/categories";
    }
}