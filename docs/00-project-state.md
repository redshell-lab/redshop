# RedShop — Project State

## 1. Purpose

RedShop is a small e-commerce application used as a practical web application security laboratory.

The application is first implemented as a functional application with normal security controls. Starting with Phase 6, selected weaknesses are intentionally introduced so they can be studied and exploited in a controlled environment.

## 2. Current State

**Current phase:** Phase 5 — Admin  
**Phase 5 status:** Completed  
**Next phase:** Phase 6 — Intentional Vulnerabilities

| Phase | Description | Status |
|---|---|---|
| 1 | Product + Category | Completed |
| 2 | User + Login + Spring Security + Profile | Completed |
| 3 | Cart + Checkout + Order | Completed |
| 4 | REST API + API Authentication + Authorization | Completed |
| 5 | Admin + Roles + Admin Operations | Completed |
| 6 | Intentional Vulnerabilities | Not started |

## 3. Technology Stack

- Java 21
- Spring Boot 4.x
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Thymeleaf
- PostgreSQL
- Maven
- Git / GitLab
- Docker

## 4. Main Packages

`com.redshell.redshop`

- `api` — REST controllers
- `admin` — admin dashboard
- `cart` — cart domain and web functionality
- `config` — security configuration
- `order` — orders, addresses, order items and admin order management
- `product` — products, categories and product administration
- `user` — users, registration, login, account and user administration

## 5. Core Entities

- User
- Product
- Category
- Cart
- CartItem
- Order
- OrderItem
- Address

## 6. Security Baseline

- Spring Security is enabled.
- Passwords are encoded through a `PasswordEncoder`.
- Form login is enabled.
- Role-based authorization is enabled.
- `/admin/**` requires `ROLE_ADMIN`.
- Protected routes require authentication.
- CSRF protection remains enabled.
- HTML POST forms include the CSRF token.
- Admin deletes use POST rather than GET.
- User order lookup uses `findByIdAndUser_Username(...)`.
- Unauthenticated `/api/**` requests use an HTTP 401 entry point.

## 7. Development Accounts

| Username | Role | Development password |
|---|---|---|
| `ehsan` | USER | `Password123!` |
| `admin` | ADMIN | `Admin123!` |

These are development-only credentials.

## 8. Admin Features

- Dashboard
- Product list/create/edit/delete
- Category list/create/edit/delete
- User list/create/edit/delete
- Order list/detail/status update

## 9. Order Statuses

```text
PENDING
CONFIRMED
SHIPPED
DELIVERED
CANCELLED
```

## 10. Important Routes

### Public

- `/`
- `/products`
- `/products/**`
- `/login`
- `/register`

### User

- `/account`
- `/cart`
- `/checkout`
- `/orders`
- `/orders/{id}`

### Admin

- `/admin`
- `/admin/products`
- `/admin/products/new`
- `/admin/products/{id}/edit`
- `/admin/categories`
- `/admin/categories/new`
- `/admin/categories/{id}/edit`
- `/admin/users`
- `/admin/users/new`
- `/admin/users/{id}/edit`
- `/admin/orders`
- `/admin/orders/{id}`

### REST

- `/api/products/**`
- `/api/users/**`
- `/api/cart/**`
- `/api/orders/**`

See `07-rest-api.md` for API details.

## 11. Phase 6 Plan

1. Stored XSS
2. SQL Injection
3. IDOR / Broken Access Control
4. SSRF
5. File Upload
6. Race Condition
7. Business Logic

For every vulnerability:

```text
Secure/current flow
      ↓
Intentional vulnerable change
      ↓
Exploit
      ↓
Document
      ↓
Remediate
      ↓
Retest
```

## 12. Development Rules

- Preserve the existing package structure unless a change is necessary.
- Avoid unnecessary refactoring.
- Keep CSRF enabled.
- Use CSRF tokens in HTML POST forms.
- Do not introduce intentional vulnerabilities before Phase 6.
- Keep each vulnerability isolated and reproducible.
- Record the exact endpoint, request, payload, root cause, impact and remediation.
- Test after each significant change.
- Keep this document updated at major milestones.

## 13. Handoff Point

**End of Phase 5 — Admin complete; Phase 6 not started.**

When continuing the project, use this document together with the relevant source files and templates. Do not assume code that is not documented here has been implemented.
