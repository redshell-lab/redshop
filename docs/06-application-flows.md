# RedShop — Application Flows

## Registration

```text
/register
  ↓
RegistrationController
  ↓
UserService.register()
  ├─ username uniqueness
  ├─ email uniqueness
  └─ password encoding
  ↓
UserRepository
```

## Login

```text
POST /login
  ↓
Spring Security
  ↓
CustomUserDetailsService
  ↓
UserRepository
  ↓
Password verification
  ↓
Authenticated session
```

## Product Browsing

```text
GET /products
  ↓
ProductController
  ↓
ProductService / Repository
  ↓
Thymeleaf
```

## Checkout

```text
/cart
  ↓
/checkout
  ↓
OrderController
  ↓
OrderService.createOrder()
  ├─ User
  ├─ Cart
  ├─ Address
  ├─ Order
  └─ OrderItems
  ↓
OrderRepository
  ↓
PostgreSQL
```

The cart is cleared after a successful order creation.

## User Order Access

```text
GET /orders/{id}
  ↓
OrderController
  ↓
OrderService.findUserOrder(username, id)
  ↓
OrderRepository.findByIdAndUser_Username(...)
```

## Admin Order Status

```text
POST /admin/orders/{id}/status
  ↓
AdminOrderController
  ↓
OrderService.updateStatus()
  ↓
OrderRepository.save()
```

## Admin Product Management

```text
Admin
  ↓
AdminProductController
  ↓
ProductService / repositories
  ↓
PostgreSQL
```
