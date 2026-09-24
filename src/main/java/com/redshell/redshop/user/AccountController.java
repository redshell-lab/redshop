package com.redshell.redshop.user;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AccountController {

    private final UserService userService;
    private final UserAddressService userAddressService;
    private final AvatarService avatarService;

    public AccountController(
            UserService userService,
            UserAddressService userAddressService,
            AvatarService avatarService
    ) {
        this.userService = userService;
        this.userAddressService = userAddressService;
        this.avatarService = avatarService;
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

        UserAddress address = userAddressService.findByUserId(
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

        userAddressService.saveOrUpdate(
                user.getId(),
                addressLine,
                city,
                postalCode,
                country
        );

        return "redirect:/account/address?updated";
    }

    @PostMapping("/account/avatar")
    public String uploadAvatar(
            Authentication authentication,
            @RequestParam("avatar") MultipartFile avatar
    ) {
        User user = userService.findByUsername(
                authentication.getName()
        );

        String filename =
                avatarService.saveAvatar(avatar);

        user.setAvatarFilename(filename);

        userService.save(user);

        return "redirect:/account/profile?avatarUpdated";
    }
}