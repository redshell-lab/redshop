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
    private final AddressService addressService;

    public AccountController(
            UserService userService,
            AddressService addressService
    ) {
        this.userService = userService;
        this.addressService = addressService;
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


    @GetMapping("/account/password")
    public String passwordPage() {
        return "user/password";
    }

    @PostMapping("/account/password")
    public String changePassword(
            Authentication authentication,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {
        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/account/password?mismatch";
        }

        User user = userService.findByUsername(
                authentication.getName()
        );

        try {
            userService.changePassword(
                    user.getId(),
                    currentPassword,
                    newPassword
            );
        } catch (IllegalArgumentException e) {
            return "redirect:/account/password?error";
        }

        return "redirect:/account/password?updated";
    }

    @GetMapping("/account/address")
    public String address(
            Authentication authentication,
            Model model
    ) {
        User user = userService.findByUsername(
                authentication.getName()
        );

        Address address = addressService.findByUserId(
                user.getId()
        );

        model.addAttribute("address", address);

        return "user/address";
    }

    @PostMapping("/account/address")
    public String updateAddress(
            Authentication authentication,
            @RequestParam String addressLine,
            @RequestParam String city,
            @RequestParam String postalCode,
            @RequestParam String country
    ) {
        User user = userService.findByUsername(
                authentication.getName()
        );

        addressService.saveOrUpdate(
                user.getId(),
                addressLine,
                city,
                postalCode,
                country
        );

        return "redirect:/account/address?updated";
    }
}