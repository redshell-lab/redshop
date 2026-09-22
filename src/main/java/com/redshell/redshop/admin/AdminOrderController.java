package com.redshell.redshop.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminOrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public AdminOrderController(
            OrderRepository orderRepository,
            OrderService orderService
    ) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/admin/orders")
    public String orders(Model model) {

        model.addAttribute(
                "orders",
                orderRepository.findAll()
        );

        return "admin/orders/list";
    }

    @GetMapping("/admin/orders/{id}")
    public String order(
            @PathVariable Long id,
            Model model
    ) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Order not found"
                        )
                );

        model.addAttribute(
                "order",
                order
        );

        return "admin/orders/detail";
    }

    @PostMapping("/admin/orders/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {

        orderService.updateStatus(
                id,
                status
        );

        return "redirect:/admin/orders/" + id;
    }
}