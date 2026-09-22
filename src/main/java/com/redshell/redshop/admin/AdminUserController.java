package com.redshell.redshop.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminUserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public AdminUserController(
            UserRepository userRepository,
            UserService userService
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String users(Model model) {

        model.addAttribute(
                "users",
                userRepository.findAll()
        );

        return "admin/users/list";
    }

    @GetMapping("/admin/users/new")
    public String newUser() {

        return "admin/users/form";
    }

    @PostMapping("/admin/users")
    public String createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String role
    ) {

        userService.create(
                username,
                email,
                password,
                firstName,
                lastName,
                role
        );

        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(
            @PathVariable Long id,
            Model model
    ) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );

        model.addAttribute(
                "user",
                user
        );

        return "admin/users/form";
    }

    @PostMapping("/admin/users/{id}")
    public String updateUser(
            @PathVariable Long id,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String role
    ) {

        userService.update(
                id,
                email,
                password,
                firstName,
                lastName,
                role
        );

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(
            @PathVariable Long id
    ) {

        userService.delete(id);

        return "redirect:/admin/users";
    }
}