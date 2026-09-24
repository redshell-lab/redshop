package com.redshell.redshop.coupon;

import java.math.BigDecimal;

public record CouponCalculation(
        Coupon coupon,
        BigDecimal discount
) {
}