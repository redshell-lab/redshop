package com.redshell.redshop.api;

import com.redshell.redshop.product.ProductService;
import com.redshell.redshop.product.dto.ProductResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> products() {

        return productService.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse product(
            @PathVariable Long id
    ) {

        return ProductResponse.from(
                productService.findById(id)
        );
    }
}