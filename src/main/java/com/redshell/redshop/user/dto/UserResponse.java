package com.redshell.redshop.user.dto;

import com.redshell.redshop.user.User;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String role
) {

    public static UserResponse from(User user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}