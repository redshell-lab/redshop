package com.redshell.redshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
public class SecurityConfig {

    private static final Logger securityLog =
            LoggerFactory.getLogger("SECURITY_EVENT");

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/products",
                                "/products/**",
                                "/api/products",
                                "/api/products/**",
                                "/login",
                                "/register",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                PathPatternRequestMatcher
                                        .withDefaults()
                                        .matcher("/api/**")
                        )
                        .accessDeniedHandler(accessDeniedHandler())
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/account", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {

                            if (authentication != null) {
                                securityLog.info(
                                        "SECURITY_EVENT LOGOUT user={} ip={}",
                                        authentication.getName(),
                                        request.getRemoteAddr()
                                );
                            }

                            response.sendRedirect("/login?logout");
                        })
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        return (request, response, accessDeniedException) -> {

            Authentication authentication =
                    org.springframework.security.core.context.SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            String username =
                    authentication != null
                            ? authentication.getName()
                            : "anonymous";

            securityLog.warn(
                    "SECURITY_EVENT ACCESS_DENIED user={} ip={} method={} uri={}",
                    username,
                    request.getRemoteAddr(),
                    request.getMethod(),
                    request.getRequestURI()
            );

            response.sendError(HttpStatus.FORBIDDEN.value());
        };
    }
}