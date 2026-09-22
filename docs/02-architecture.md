# RedShop — Architecture

## High-Level Architecture

```text
Browser
   |
   v
Spring Security
   |
   v
Spring MVC / Thymeleaf Controllers
   |
   +----------------------+-------------------+
   |                                          |
   v                                          v
Services                                REST Controllers
   |                                          |
   +----------------------+-------------------+
                          |
                          v
                   Spring Data JPA
                          |
                          v
                      Hibernate
                          |
                          v
                      PostgreSQL
```

## Layers

### Presentation

Web controllers return Thymeleaf views. REST controllers return API responses.

### Service

Services contain reusable application logic:

- `ProductService`
- `UserService`
- `CartService`
- `OrderService`

### Repository

Spring Data repositories handle persistence:

- `ProductRepository`
- `CategoryRepository`
- `UserRepository`
- `CartRepository`
- `CartItemRepository`
- `OrderRepository`

### Security

`SecurityConfig` defines authentication, authorization, login/logout, public routes, API authentication behavior and leaves CSRF enabled.

`CustomUserDetailsService` loads users from the database.

## Design Principle

The architecture intentionally stays simple so each security issue can be traced through controller → service → repository → database without unnecessary framework complexity.
