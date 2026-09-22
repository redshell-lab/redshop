package com.redshell.redshop.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDataInitializer {

    @Bean
    CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (!userRepository.existsByUsername("ehsan")) {

                User user = new User(
                        "ehsan",
                        "ehsan@redshop.local",
                        passwordEncoder.encode("Password123!"),
                        "Ehsan",
                        "RedShell",
                        "USER"
                );

                userRepository.save(user);
            }

            if (!userRepository.existsByUsername("admin")) {

                User admin = new User(
                        "admin",
                        "admin@redshop.local",
                        passwordEncoder.encode("Admin123!"),
                        "RedShop",
                        "Admin",
                        "ADMIN"
                );

                userRepository.save(admin);
            }
        };
    }
}