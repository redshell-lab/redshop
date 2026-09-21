package com.redshell.redshop.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cart(
            Principal principal,
            Model model
    ) {

        Cart cart = cartService.getOrCreateCart(
                principal.getName()
        );

        model.addAttribute("cart", cart);

        return "cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(
            Principal principal,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity
    ) {

        cartService.addToCart(
                principal.getName(),
                productId,
                quantity
        );

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String update(
            Principal principal,
            @RequestParam Long itemId,
            @RequestParam Integer quantity
    ) {

        cartService.updateQuantity(
                principal.getName(),
                itemId,
                quantity
        );

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(
            Principal principal,
            @RequestParam Long itemId
    ) {

        cartService.removeItem(
                principal.getName(),
                itemId
        );

        return "redirect:/cart";
    }
}