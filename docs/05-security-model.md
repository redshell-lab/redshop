# RedShop — Security Model

## Authentication

Spring Security handles form authentication using `SecurityConfig` and `CustomUserDetailsService`.

## Passwords

The application uses `PasswordEncoderFactories.createDelegatingPasswordEncoder()` and does not store development passwords as plaintext.

## Roles

- `USER`
- `ADMIN`

Spring Security represents these as `ROLE_USER` and `ROLE_ADMIN`.

## Authorization

`/admin/**` requires `ROLE_ADMIN`.

## Public Routes

```text
/
/products
/products/**
/api/products
/api/products/**
/login
/register
```

Other routes require authentication unless explicitly permitted.

## CSRF

CSRF is enabled. HTML POST forms include the generated token. Admin create/update/delete operations therefore require a valid token.

## REST Authentication

Unauthenticated `/api/**` requests use an HTTP 401 entry point rather than the normal HTML login redirect.

## Object-Level Authorization Baseline

User order access is constrained with:

```text
findByIdAndUser_Username(orderId, username)
```

This is an important baseline before the planned IDOR lab.

## Phase 6 Rule

The current implementation is the secure baseline. Vulnerabilities should be introduced deliberately, one at a time, and then fixed and retested.
