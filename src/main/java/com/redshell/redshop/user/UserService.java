package com.redshell.redshop.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(
            String username,
            String email,
            String password,
            String firstName,
            String lastName
    ) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        String encodedPassword =
                passwordEncoder.encode(password);

        User user = new User(
                username,
                email,
                encodedPassword,
                firstName,
                lastName,
                "USER"
        );

        return userRepository.save(user);
    }

    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));
    }

    public User create(
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            String role
    ) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Username already exists"
            );
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email already exists"
            );
        }

        String encodedPassword =
                passwordEncoder.encode(password);

        User user = new User(
                username,
                email,
                encodedPassword,
                firstName,
                lastName,
                role
        );

        return userRepository.save(user);
    }

    public User update(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            String role
    ) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );

        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);

        if (password != null && !password.isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(password)
            );
        }

        return userRepository.save(user);
    }

    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );

        userRepository.delete(user);
    }
}