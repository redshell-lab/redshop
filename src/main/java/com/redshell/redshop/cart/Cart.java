package com.redshell.redshop.cart;

import com.redshell.redshop.coupon.Coupon;
import com.redshell.redshop.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon appliedCoupon;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Coupon getAppliedCoupon() {
        return appliedCoupon;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setAppliedCoupon(Coupon appliedCoupon) {
        this.appliedCoupon = appliedCoupon;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        return getSubtotal()
                .subtract(discount)
                .max(BigDecimal.ZERO);
    }
}