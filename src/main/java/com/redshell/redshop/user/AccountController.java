package com.redshell.redshop.user;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String account(
            Authentication authentication,
            Model model
    ) {
        User user = userService.findByUsername(
                authentication.getName()
        );

        model.addAttribute("user", user);

        return "user/account";
    }

    @GetMapping("/account/profile")
    public String profile(
            Authentication authentication,
            Model model
    ) {
        User user = userService.findByUsername(
                authentication.getName()
        );

        model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("/account/profile")
    public String updateProfile(
            Authentication authentication,
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        User user = userService.findByUsername(
                authentication.getName()
        );

        userService.updateProfile(
                user.getId(),
                email,
                firstName,
                lastName
        );

        return "redirect:/account/profile?updated";
    }
}