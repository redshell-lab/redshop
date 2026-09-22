# RedShop — Project Structure

## Java Source

```text
src/main/java/com/redshell/redshop/
├── api/
│   ├── CartRestController.java
│   ├── OrderRestController.java
│   ├── ProductRestController.java
│   └── UserRestController.java
├── admin/
│   └── AdminController.java
├── cart/
│   ├── dto/
│   ├── Cart.java
│   ├── CartController.java
│   ├── CartItem.java
│   ├── CartItemRepository.java
│   ├── CartRepository.java
│   └── CartService.java
├── config/
│   └── SecurityConfig.java
├── order/
│   ├── dto/
│   │   ├── CreateOrderRequest.java
│   │   ├── OrderItemResponse.java
│   │   └── OrderResponse.java
│   ├── Address.java
│   ├── AdminOrderController.java
│   ├── Order.java
│   ├── OrderController.java
│   ├── OrderItem.java
│   ├── OrderRepository.java
│   ├── OrderService.java
│   └── OrderStatus.java
├── product/
│   ├── dto/
│   ├── AdminCategoryController.java
│   ├── AdminProductController.java
│   ├── Category.java
│   ├── CategoryRepository.java
│   ├── DataInitializer.java
│   ├── Product.java
│   ├── ProductController.java
│   ├── ProductRepository.java
│   └── ProductService.java
├── user/
│   ├── dto/
│   ├── AccountController.java
│   ├── AdminUserController.java
│   ├── CustomUserDetailsService.java
│   ├── LoginController.java
│   ├── RegistrationController.java
│   ├── User.java
│   ├── UserDataInitializer.java
│   ├── UserRepository.java
│   └── UserService.java
└── RedshopApplication.java
```

## Templates

```text
src/main/resources/templates/
├── admin/
│   ├── dashboard.html
│   ├── products/{list.html,form.html}
│   ├── categories/{list.html,form.html}
│   ├── users/{list.html,form.html}
│   └── orders/{list.html,detail.html}
├── cart/cart.html
├── order/{checkout.html,detail.html,list.html}
├── products/{detail.html,list.html}
└── user/{account.html,login.html,register.html}
```

## Responsibilities

- `product` — catalog and product/category administration
- `user` — users, authentication integration and user administration
- `cart` — shopping cart
- `order` — checkout and orders
- `api` — REST APIs
- `config` — security
- `admin` — dashboard
