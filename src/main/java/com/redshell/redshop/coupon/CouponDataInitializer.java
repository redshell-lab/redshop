package com.redshell.redshop.coupon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class CouponDataInitializer {

    @Bean
    CommandLineRunner initCoupons(
            CouponRepository couponRepository
    ) {
        return args -> {

            if (!couponRepository.existsByCode("SAVE20")) {

                Coupon coupon = new Coupon(
                        "SAVE20",
                        DiscountType.PERCENTAGE,
                        new BigDecimal("20"),
                        LocalDateTime.now().plusDays(30),
                        10,
                        1
                );

                couponRepository.save(coupon);
            }
        };
    }
}