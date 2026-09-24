package com.redshell.redshop.coupon;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Integer usageLimit;

    @Column(nullable = false)
    private Integer usedCount = 0;

    @Column(nullable = false)
    private Integer perUserLimit;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Coupon() {
    }

    public Coupon(
            String code,
            DiscountType discountType,
            BigDecimal discountValue,
            LocalDateTime expiresAt,
            Integer usageLimit,
            Integer perUserLimit
    ) {
        this.code = code;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.expiresAt = expiresAt;
        this.usageLimit = usageLimit;
        this.perUserLimit = perUserLimit;
        this.usedCount = 0;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public Integer getPerUserLimit() {
        return perUserLimit;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }
}