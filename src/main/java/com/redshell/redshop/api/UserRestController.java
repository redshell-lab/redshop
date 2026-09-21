package com.redshell.redshop.api;

import com.redshell.redshop.user.User;
import com.redshell.redshop.user.UserService;
import com.redshell.redshop.user.dto.UserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse me(
            Authentication authentication
    ) {

        User user = userService.findByUsername(
                authentication.getName()
        );

        return UserResponse.from(user);
    }
}