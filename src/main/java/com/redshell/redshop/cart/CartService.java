package com.redshell.redshop.cart;

import com.redshell.redshop.product.Product;
import com.redshell.redshop.product.ProductService;
import com.redshell.redshop.user.User;
import com.redshell.redshop.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserService userService;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductService productService,
            UserService userService
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional
    public Cart getOrCreateCart(String username) {

        return cartRepository
                .findByUser_Username(username)
                .orElseGet(() -> {

                    User user = userService.findByUsername(username);

                    Cart cart = new Cart(user);

                    return cartRepository.save(cart);
                });
    }

    @Transactional
    public void addToCart(
            String username,
            Long productId,
            Integer quantity
    ) {

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        Cart cart = getOrCreateCart(username);

        Product product = productService.findById(productId);

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (item == null) {

            if (quantity > product.getStock()) {
                throw new IllegalArgumentException(
                        "Not enough stock"
                );
            }

            item = new CartItem(product, quantity);
            cart.addItem(item);

        } else {

            int newQuantity = item.getQuantity() + quantity;

            if (newQuantity > product.getStock()) {
                throw new IllegalArgumentException(
                        "Not enough stock"
                );
            }

            item.setQuantity(newQuantity);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void updateQuantity(
            String username,
            Long itemId,
            Integer quantity
    ) {

        if (quantity == null || quantity <= 0) {
            removeItem(username, itemId);
            return;
        }

        Cart cart = getOrCreateCart(username);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cart item not found")
                );

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Invalid cart item");
        }

        if (quantity > item.getProduct().getStock()) {
            throw new IllegalArgumentException("Not enough stock");
        }

        item.setQuantity(quantity);

        cartItemRepository.save(item);
    }

    @Transactional
    public void removeItem(
            String username,
            Long itemId
    ) {

        Cart cart = getOrCreateCart(username);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cart item not found")
                );

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Invalid cart item");
        }

        cart.removeItem(item);

        cartRepository.save(cart);
    }
}