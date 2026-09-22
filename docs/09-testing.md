# RedShop — Testing

## Authentication

### Anonymous

Verify public product pages, login and registration work, while protected pages require authentication.

### USER

Verify account, cart, checkout, orders work and `/admin/**` returns 403.

### ADMIN

Verify dashboard and all admin management operations work.

## CSRF

For every HTML POST:

1. valid CSRF token → success
2. missing/invalid token → rejected

## Order Isolation

Create an order as user A. As user B, request user A's order ID. The application must not return user A's order.

## Admin Regression Tests

- create/edit/delete product
- create/edit/delete category
- create/edit/delete user
- view order
- update order status

## API Tests

For each endpoint test anonymous, authenticated, unauthorized-object, invalid-input and missing-resource cases.

## Phase 6 Testing

Each vulnerability must include a baseline request, vulnerable request, exploit result, remediation and retest.

Burp Suite can be used to capture and modify requests.

## Regression

After every Phase 6 change retest login, products, cart, checkout, orders, admin and REST APIs.
