package com.redshell.redshop.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_UsernameOrderByCreatedAtDesc(
            String username
    );

    Optional<Order> findByIdAndUser_Username(
            Long id,
            String username
    );
}