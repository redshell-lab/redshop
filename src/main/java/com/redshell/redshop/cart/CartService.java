package com.redshell.redshop.cart;

import com.redshell.redshop.coupon.Coupon;
import com.redshell.redshop.coupon.CouponCalculation;
import com.redshell.redshop.coupon.CouponService;
import com.redshell.redshop.product.Product;
import com.redshell.redshop.product.ProductService;
import com.redshell.redshop.user.User;
import com.redshell.redshop.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CouponService couponService;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductService productService,
            UserService userService,
            CouponService couponService
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.userService = userService;
        this.couponService = couponService;
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

            int newQuantity =
                    item.getQuantity() + quantity;

            if (newQuantity > product.getStock()) {
                throw new IllegalArgumentException(
                        "Not enough stock"
                );
            }

            item.setQuantity(newQuantity);
        }

        refreshCouponDiscount(cart);

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
                        new IllegalArgumentException(
                                "Cart item not found"
                        )
                );

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException(
                    "Invalid cart item"
            );
        }

        if (quantity > item.getProduct().getStock()) {
            throw new IllegalArgumentException(
                    "Not enough stock"
            );
        }

        item.setQuantity(quantity);

        refreshCouponDiscount(cart);

        cartItemRepository.save(item);
        cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(
            String username,
            Long itemId
    ) {

        Cart cart = getOrCreateCart(username);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Cart item not found"
                        )
                );

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException(
                    "Invalid cart item"
            );
        }

        cart.removeItem(item);

        refreshCouponDiscount(cart);

        cartRepository.save(cart);
    }

    @Transactional
    public void applyCoupon(
            String username,
            String code
    ) {

        User user = userService.findByUsername(username);

        Cart cart = getOrCreateCart(username);

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot apply coupon to an empty cart"
            );
        }

        CouponCalculation calculation =
                couponService.validateAndCalculate(
                        code,
                        user,
                        cart.getSubtotal()
                );

        cart.setAppliedCoupon(calculation.coupon());
        cart.setDiscount(calculation.discount());

        cartRepository.save(cart);
    }

    @Transactional
    public void removeCoupon(String username) {

        Cart cart = getOrCreateCart(username);

        cart.setAppliedCoupon(null);
        cart.setDiscount(BigDecimal.ZERO);

        cartRepository.save(cart);
    }

    private void refreshCouponDiscount(Cart cart) {

        Coupon coupon = cart.getAppliedCoupon();

        if (coupon == null) {
            cart.setDiscount(BigDecimal.ZERO);
            return;
        }

        BigDecimal discount =
                couponService.calculateDiscount(
                        coupon,
                        cart.getSubtotal()
                );

        cart.setDiscount(discount);
    }
}