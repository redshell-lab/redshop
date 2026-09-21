package com.redshell.redshop.order;

import com.redshell.redshop.cart.Cart;
import com.redshell.redshop.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    public OrderController(
            CartService cartService,
            OrderService orderService
    ) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String checkout(
            Principal principal,
            Model model
    ) {

        Cart cart = cartService.getOrCreateCart(
                principal.getName()
        );

        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cart", cart);

        return "order/checkout";
    }

    @PostMapping("/checkout")
    public String createOrder(
            Principal principal,
            @RequestParam String fullName,
            @RequestParam String street,
            @RequestParam String city,
            @RequestParam String postalCode,
            @RequestParam String phone
    ) {

        Order order = orderService.createOrder(
                principal.getName(),
                fullName,
                street,
                city,
                postalCode,
                phone
        );

        return "redirect:/orders/" + order.getId();
    }

    @GetMapping("/orders")
    public String orders(
            Principal principal,
            Model model
    ) {

        model.addAttribute(
                "orders",
                orderService.findUserOrders(
                        principal.getName()
                )
        );

        return "order/list";
    }

    @GetMapping("/orders/{id}")
    public String order(
            Principal principal,
            @PathVariable Long id,
            Model model
    ) {

        Order order = orderService.findUserOrder(
                principal.getName(),
                id
        );

        model.addAttribute("order", order);

        return "order/detail";
    }
}