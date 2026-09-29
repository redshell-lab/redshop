package com.redshell.redshop.admin;

import com.redshell.redshop.order.OrderRepository;
import com.redshell.redshop.product.ProductRepository;
import com.redshell.redshop.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final DiagnosticService diagnosticService;

    public AdminController(
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            DiagnosticService diagnosticService
    ) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.diagnosticService = diagnosticService;
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

    @GetMapping("/admin/diagnostics")
    public String diagnostics() {
        return "admin/diagnostics";
    }

    @PostMapping("/admin/diagnostics/ping")
    public String ping(
            @RequestParam String host,
            Model model
    ) {

        String output =
                diagnosticService.ping(host);

        model.addAttribute("host", host);
        model.addAttribute("output", output);

        return "admin/diagnostics";
    }
}