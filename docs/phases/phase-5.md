# Phase 5 — Admin + Roles + Admin Operations

## Goal

Create an administrator area with role-based access and management operations.

## Implemented

- Admin dashboard
- Product CRUD
- Category CRUD
- User CRUD and role management
- Order list/detail
- Order status update

## Security

`/admin/**` requires `ROLE_ADMIN`. CSRF remains enabled for HTML POST requests. Delete operations use POST.

## Validation

- ADMIN can access admin pages.
- USER receives 403 on admin pages.
- Product/category/user admin operations work.
- Order status update works.
- CSRF protection is required for POST operations.

## Result

**Completed.**

## Next

Phase 6 — Intentional Vulnerabilities.
