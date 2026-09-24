package com.redshell.redshop.user;

import org.springframework.stereotype.Service;

@Service
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    public UserAddressService(
            UserAddressRepository userAddressRepository,
            UserRepository userRepository
    ) {
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
    }

    public UserAddress findByUserId(Long userId) {
        return userAddressRepository
                .findByUserId(userId)
                .orElse(null);
    }

    public UserAddress saveOrUpdate(
            Long userId,
            String addressLine,
            String city,
            String postalCode,
            String country
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        UserAddress userAddress = userAddressRepository
                .findByUserId(userId)
                .orElseGet(() ->
                        new UserAddress(
                                addressLine,
                                city,
                                postalCode,
                                country,
                                user
                        )
                );

        userAddress.setAddressLine(addressLine);
        userAddress.setCity(city);
        userAddress.setPostalCode(postalCode);
        userAddress.setCountry(country);

        return userAddressRepository.save(userAddress);
    }
}