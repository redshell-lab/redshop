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

    @GetMapping("/internal/status")
    @ResponseBody
    public String internalStatus() {
        return "RedShop internal service is reachable";
    }

    @GetMapping("/products/preview-image")
    @ResponseBody
    public String previewImage(
            @RequestParam String url
    ) {
        try {
            java.net.URI uri = java.net.URI.create(url);

            java.net.http.HttpClient client =
                    java.net.http.HttpClient.newHttpClient();

            java.net.http.HttpRequest request =
                    java.net.http.HttpRequest.newBuilder()
                            .uri(uri)
                            .GET()
                            .build();

            java.net.http.HttpResponse<String> response =
                    client.send(
                            request,
                            java.net.http.HttpResponse.BodyHandlers.ofString()
                    );

            return response.body();

        } catch (Exception e) {
            return "Could not fetch resource";
        }
    }
}