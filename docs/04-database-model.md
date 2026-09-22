# RedShop — Database Model

## Relationships

```text
Category 1 -------- N Product

User 1 ------------ 1 Cart
Cart 1 ------------ N CartItem
Product 1 --------- N CartItem

User 1 ------------ N Order
Order 1 ----------- 1 Address
Order 1 ----------- N OrderItem
Product 1 --------- N OrderItem
```

## User

Fields: `id`, `username`, `email`, `password`, `firstName`, `lastName`, `role`.

Username and email are unique.

## Product

Fields: `id`, `name`, `description`, `price`, `stock`, `category`.

## Category

Fields: `id`, `name`.

Category name is unique.

## Cart / CartItem

A cart belongs to a user. Cart items reference products and quantities.

## Order

Fields: user, address, status, total, createdAt, items.

Status is stored using `EnumType.STRING`.

## OrderItem

Fields: order, product, quantity, unitPrice. Subtotal is `unitPrice × quantity`.

## Address

Fields: fullName, street, city, postalCode, phone.

The order owns its address through a cascading one-to-one relationship.

## Persistence Behavior

`Order.items` uses cascade all and orphan removal. During checkout, order items are created from cart items and the cart is cleared after order creation.
