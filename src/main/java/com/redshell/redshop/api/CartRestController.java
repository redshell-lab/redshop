package com.redshell.redshop.api;

import com.redshell.redshop.cart.Cart;
import com.redshell.redshop.cart.CartService;
import com.redshell.redshop.cart.dto.AddToCartRequest;
import com.redshell.redshop.cart.dto.CartResponse;
import com.redshell.redshop.cart.dto.UpdateCartItemRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public CartResponse cart(
            Principal principal
    ) {

        Cart cart = cartService.getOrCreateCart(
                principal.getName()
        );

        return CartResponse.from(cart);
    }

    @PostMapping("/items")
    public CartResponse addItem(
            Principal principal,
            @RequestBody AddToCartRequest request
    ) {

        cartService.addToCart(
                principal.getName(),
                request.productId(),
                request.quantity()
        );

        Cart cart = cartService.getOrCreateCart(
                principal.getName()
        );

        return CartResponse.from(cart);
    }

    @PutMapping("/items/{itemId}")
    public CartResponse updateItem(
            Principal principal,
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemRequest request
    ) {

        cartService.updateQuantity(
                principal.getName(),
                itemId,
                request.quantity()
        );

        Cart cart = cartService.getOrCreateCart(
                principal.getName()
        );

        return CartResponse.from(cart);
    }

    @DeleteMapping("/items/{itemId}")
    public CartResponse removeItem(
            Principal principal,
            @PathVariable Long itemId
    ) {

        cartService.removeItem(
                principal.getName(),
                itemId
        );

        Cart cart = cartService.getOrCreateCart(
                principal.getName()
        );

        return CartResponse.from(cart);
    }
}