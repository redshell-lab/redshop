package com.redshell.redshop.user;

import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
            AddressRepository addressRepository,
            UserRepository userRepository
    ) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address findByUserId(Long userId) {
        return addressRepository
                .findByUserId(userId)
                .orElse(null);
    }

    public Address saveOrUpdate(
            Long userId,
            String addressLine,
            String city,
            String postalCode,
            String country
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Address address = addressRepository
                .findByUserId(userId)
                .orElseGet(() ->
                        new Address(
                                addressLine,
                                city,
                                postalCode,
                                country,
                                user
                        )
                );

        address.setAddressLine(addressLine);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setCountry(country);

        return addressRepository.save(address);
    }
}