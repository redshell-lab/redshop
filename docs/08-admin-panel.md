# RedShop — Admin Panel

## Access Control

```text
/admin/** → ROLE_ADMIN
```

## Dashboard

`GET /admin`

Displays counts for users, products and orders.

## Product Administration

```text
GET  /admin/products
GET  /admin/products/new
POST /admin/products
GET  /admin/products/{id}/edit
POST /admin/products/{id}
POST /admin/products/{id}/delete
```

## Category Administration

```text
GET  /admin/categories
GET  /admin/categories/new
POST /admin/categories
GET  /admin/categories/{id}/edit
POST /admin/categories/{id}
POST /admin/categories/{id}/delete
```

## User Administration

Provides list/create/edit/delete and role management.

## Order Administration

```text
GET  /admin/orders
GET  /admin/orders/{id}
POST /admin/orders/{id}/status
```

The order detail page displays customer, userAddress, order information, items and status update controls.

## CSRF

All admin POST forms include the Spring Security CSRF token. Deletes use POST rather than GET.
