package com.redshell.redshop.coupon;

import com.redshell.redshop.order.Order;
import com.redshell.redshop.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_usages")
public class CouponUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false)
    private LocalDateTime usedAt;

    public CouponUsage() {
    }

    public CouponUsage(
            Coupon coupon,
            User user,
            Order order
    ) {
        this.coupon = coupon;
        this.user = user;
        this.order = order;
        this.usedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public User getUser() {
        return user;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }
}