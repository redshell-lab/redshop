# RedShop — REST API

## Controllers

- `ProductRestController`
- `UserRestController`
- `CartRestController`
- `OrderRestController`

Located under:

```text
src/main/java/com/redshell/redshop/api/
```

## Product API

Product endpoints are explicitly allowed under:

```text
/api/products
/api/products/**
```

## Authentication

Protected API operations require authentication. Unauthenticated `/api/**` requests receive HTTP 401 through the API-specific entry point.

## Authorization Testing

For every API endpoint test:

- anonymous access
- authenticated access
- object ownership
- role restrictions
- invalid input
- missing resources
- unsupported methods

## DTOs

Order API DTOs include:

- `CreateOrderRequest`
- `OrderResponse`
- `OrderItemResponse`

DTOs keep REST representations separate from JPA entities.
