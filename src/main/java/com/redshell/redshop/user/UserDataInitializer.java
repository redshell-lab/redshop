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

            if (userRepository.count() > 0) {
                return;
            }

            User user = new User(
                    "ehsan",
                    "ehsan@redshop.local",
                    passwordEncoder.encode("ehsan"),
                    "Ehsan",
                    "RedShell",
                    "USER"
            );

            userRepository.save(user);
        };
    }
}