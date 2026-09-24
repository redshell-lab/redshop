package com.redshell.redshop.coupon;

import com.redshell.redshop.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;

    public CouponService(
            CouponRepository couponRepository,
            CouponUsageRepository couponUsageRepository
    ) {
        this.couponRepository = couponRepository;
        this.couponUsageRepository = couponUsageRepository;
    }

    public CouponCalculation validateAndCalculate(
            String code,
            User user,
            BigDecimal subtotal
    ) {
        Coupon coupon = couponRepository
                .findByCode(code)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid coupon code"));

        if (!coupon.isActive()) {
            throw new IllegalArgumentException("Coupon is inactive");
        }

        if (!LocalDateTime.now().isBefore(coupon.getExpiresAt())) {
            throw new IllegalArgumentException("Coupon has expired");
        }

        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new IllegalArgumentException(
                    "Coupon usage limit has been reached"
            );
        }

        long userUsageCount =
                couponUsageRepository.countByCouponIdAndUserId(
                        coupon.getId(),
                        user.getId()
                );

        if (userUsageCount >= coupon.getPerUserLimit()) {
            throw new IllegalArgumentException(
                    "You have already used this coupon"
            );
        }

        BigDecimal discount;

        if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {

            discount = subtotal
                    .multiply(coupon.getDiscountValue())
                    .divide(
                            BigDecimal.valueOf(100),
                            2,
                            RoundingMode.HALF_UP
                    );

        } else {

            discount = coupon.getDiscountValue()
                    .min(subtotal);
        }

        discount = discount.setScale(
                2,
                RoundingMode.HALF_UP
        );

        return new CouponCalculation(
                coupon,
                discount
        );
    }
}