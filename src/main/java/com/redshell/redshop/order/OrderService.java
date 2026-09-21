package com.redshell.redshop.order;

import com.redshell.redshop.cart.Cart;
import com.redshell.redshop.cart.CartItem;
import com.redshell.redshop.cart.CartRepository;
import com.redshell.redshop.user.User;
import com.redshell.redshop.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserService userService;

    public OrderService(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            UserService userService
    ) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    @Transactional
    public Order createOrder(
            String username,
            String fullName,
            String street,
            String city,
            String postalCode,
            String phone
    ) {

        User user = userService.findByUsername(username);

        Cart cart = cartRepository
                .findByUser_Username(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Cart not found"
                        )
                );

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cart is empty"
            );
        }

        Address address = new Address(
                fullName,
                street,
                city,
                postalCode,
                phone
        );

        BigDecimal total = cart.getTotal();

        Order order = new Order(
                user,
                address,
                total
        );

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem(
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()
            );

            order.addItem(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public List<Order> findUserOrders(String username) {

        return orderRepository
                .findByUser_UsernameOrderByCreatedAtDesc(username);
    }

    public Order findUserOrder(
            String username,
            Long orderId
    ) {

        return orderRepository
                .findByIdAndUser_Username(
                        orderId,
                        username
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Order not found"
                        )
                );
    }
}