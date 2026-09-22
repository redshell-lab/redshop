package com.redshell.redshop.admin;

import com.redshell.redshop.order.OrderRepository;
import com.redshell.redshop.product.ProductRepository;
import com.redshell.redshop.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public AdminController(
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository
    ) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {

        model.addAttribute(
                "userCount",
                userRepository.count()
        );

        model.addAttribute(
                "productCount",
                productRepository.count()
        );

        model.addAttribute(
                "orderCount",
                orderRepository.count()
        );

        return "admin/dashboard";
    }
}