package com.redshell.redshop.api;

import com.redshell.redshop.order.Order;
import com.redshell.redshop.order.OrderService;
import com.redshell.redshop.order.dto.CreateOrderRequest;
import com.redshell.redshop.order.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponse> orders(
            Principal principal
    ) {

        return orderService
                .findUserOrders(principal.getName())
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderResponse order(
            Principal principal,
            @PathVariable Long id
    ) {

        return OrderResponse.from(
                orderService.findUserOrder(
                        principal.getName(),
                        id
                )
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(
            Principal principal,
            @RequestBody CreateOrderRequest request
    ) {

        Order order = orderService.createOrder(
                principal.getName(),
                request.fullName(),
                request.street(),
                request.city(),
                request.postalCode(),
                request.phone()
        );

        return OrderResponse.from(order);
    }
}