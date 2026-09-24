package com.redshell.redshop.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAddressRepository
        extends JpaRepository<UserAddress, Long> {

    Optional<UserAddress> findByUserId(Long userId);
}