package com.redshell.redshop.review;

import com.redshell.redshop.user.User;
import com.redshell.redshop.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(
            ReviewService reviewService,
            UserService userService
    ) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/{productId}/reviews")
    public String createReview(
            @PathVariable Long productId,
            @RequestParam String content,
            Authentication authentication
    ) {
        User user = userService.findByUsername(
                authentication.getName()
        );

        reviewService.create(
                productId,
                content,
                user
        );

        return "redirect:/products/" + productId;
    }
}