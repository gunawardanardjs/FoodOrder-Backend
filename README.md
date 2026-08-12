
## 📋 Table of Contents

1. [Project Overview](#-project-overview)
2. [Tech Stack](#-tech-stack)
3. [Project Structure](#-project-structure)
4. [Getting Started](#-getting-started)
5. [Configuration](#-configuration)
6. [Database Schema](#-database-schema)
7. [API Reference](#-api-reference)
8. [Authentication & Security](#-authentication--security)
9. [Default Admin Account](#-default-admin-account)
10. [Error Handling](#-error-handling)
11. [Logging](#-logging)

---

## 🎯 Project Overview

FoodieExpress is a **REST API** for an online food ordering system. It uses **Spring Boot** and **MySQL** to manage users, food items, carts, orders, and payments.

### Main Areas

| Domain | Description |
|--------|-------------|
| **Users** | Registration, login, role-based access (ADMIN / CUSTOMER) |
| **Categories** | Food menu categories with one-to-many food items |
| **Food Items** | Menu items with price, image, availability status |
| **Cart** | Per-user shopping cart with real-time total calculation |
| **Orders** | Full order lifecycle (PLACED → PREPARING → DELIVERED) |
| **Payments** | Payment recording linked one-to-one with each order |

---

## 🛠 Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Core language |
| **Spring Boot** | 3.5.4 | Application framework |
| **Spring Security** | (included) | Authentication & authorisation |
| **Spring Data JPA** | (included) | Database persistence layer |
| **MySQL** | 8.x | Relational database |
| **JJWT** | 0.13.1 | JSON Web Token generation & validation |
| **Lombok** | Latest | Boilerplate reduction |
| **ModelMapper** | 3.2.2 | Entity ↔ DTO mapping |
| **Maven** | 3.x | Build & dependency management |

---

## 📁 Project Structure

```
src
│
├── FoodOrderApplication.java          ← Entry point + ModelMapper bean
│
├── config/
│   ├── CORSConfig.java                ← CORS settings (ports 3000 & 5173)
│   └── DataInitializer.java           ← Seeds default admin on first startup
│
├── controller/
│   ├── secure/
│   │   └── AuthController.java        ← POST /auth/login, /auth/signup
│   ├── UserController.java
│   ├── CategoryController.java
│   ├── FoodController.java
│   ├── CartController.java
│   ├── OrderController.java
│   └── PaymentController.java
│
├── dao/                               ← DAO interfaces (data access layer)
│   ├── UserDao.java
│   ├── CategoryDao.java
│   ├── FoodItemDao.java
│   ├── CartDao.java
│   ├── CartItemDao.java
│   ├── OrderDao.java
│   └── PaymentDao.java
│   └── daoImpl/                       ← Concrete DAO implementations
│       ├── UserDaoImpl.java
│       ├── CategoryDaoImpl.java
│       ├── FoodItemDaoImpl.java
│       ├── CartDaoImpl.java
│       ├── CartItemDaoImpl.java
│       ├── OrderDaoImpl.java
│       └── PaymentDaoImpl.java
│
├── dto/
│   ├── request/                       ← Inbound request bodies
│   │   ├── SignUpRequestDto.java
│   │   ├── CategoryRequestDto.java
│   │   ├── FoodItemRequestDto.java
│   │   ├── CartItemRequestDto.java
│   │   ├── OrderRequestDto.java
│   │   └── PaymentRequestDto.java
│   ├── response/                      ← Outbound response bodies
│   │   ├── UserDto.java
│   │   ├── CategoryResponseDto.java
│   │   ├── FoodItemResponseDto.java
│   │   ├── CartResponseDto.java
│   │   ├── CartItemResponseDto.java
│   │   ├── OrderResponseDto.java
│   │   ├── OrderItemResponseDto.java
│   │   └── PaymentResponseDto.java
│   └── secure/
│       ├── LoginDto.java              ← Login credentials
│       └── JWTResponseDto.java        ← Token + user info on auth success
│
├── entity/
│   ├── UserEntity.java
│   ├── CategoryEntity.java
│   ├── FoodItemEntity.java
│   ├── CartEntity.java
│   ├── CartItemEntity.java
│   ├── OrderEntity.java
│   ├── OrderItemEntity.java
│   ├── PaymentEntity.java
│   └── enums/
│       ├── Role.java                  ← ADMIN | CUSTOMER
│       ├── FoodItemStatus.java        ← AVAILABLE | OUT_OF_STOCK
│       ├── OrderStatus.java           ← PLACED | PREPARING | DELIVERED | CANCELLED
│       └── PaymentStatus.java         ← PENDING | COMPLETED | FAILED
│
├── exception/
│   ├── GlobalExceptionHandler.java    ← @RestControllerAdvice — central error handling
│   ├── DataNotFoundException.java
│   ├── DuplicateResourceException.java
│   ├── BadRequestException.java
│   ├── DataSaveException.java
│   └── UnauthorizedException.java
│
├── repository/                        ← Spring Data JPA repositories
│   ├── UserRepository.java
│   ├── CategoryRepository.java
│   ├── FoodItemRepository.java
│   ├── CartRepository.java
│   ├── CartItemRepository.java
│   ├── OrderRepository.java
│   ├── OrderItemRepository.java
│   └── PaymentRepository.java
│
├── securityConfig/
│   ├── WebSecurity.java               ← SecurityFilterChain, role-based URL rules
│   ├── JwtAuthFilter.java             ← JWT validation on every request
│   ├── JWTUtils.java                  ← Token generation & parsing (jjwt 0.13)
│   ├── AuthEntryPoint.java            ← 401 response for unauthenticated requests
│   └── UserDetailServiceIMPL.java     ← Loads UserEntity for Spring Security
│
├── service/
│   ├── secure/
│   │   ├── AuthService.java
│   │   └── impl/
│   │       └── AuthServiceImpl.java
│   ├── UserService.java
│   ├── CategoryService.java
│   ├── FoodItemService.java
│   ├── CartService.java
│   ├── OrderService.java
│   ├── PaymentService.java
│   └── serviceImpl/
│       ├── UserServiceImpl.java
│       ├── CategoryServiceImpl.java
│       ├── FoodItemServiceImpl.java
│       ├── CartServiceImpl.java
│       ├── OrderServiceImpl.java
│       └── PaymentServiceImpl.java
│
└── util/
    ├── IDGenerator.java               ← UUID-based ID generators
    ├── MappingDtoEntity.java          ← Entity ↔ DTO mapping methods
    ├── CustomStatus.java
    └── DateTimeUtil.java
```

---

## 🚀 How to Run

### Prerequisites

Install these before running the project:

- Java 21 or above
- Maven 3.6+
- MySQL 8.x
- Git

### 1 — Clone the repository

```bash
git clone https://github.com/gunawardanardjs/FoodOrder-Backend.git
cd foodorder-backend
```

### 2 — Create the database

Log in to MySQL and run:

```sql
CREATE DATABASE food_ordering_db;
```

> **Tip:** With `createDatabaseIfNotExist=true`, the database can be created automatically.

### 3 — Configure credentials

Open `src/main/resources/application-dev.properties` and update:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 4 — Build & run

```bash
mvn clean install
mvn spring-boot:run
```

The server starts on **http://localhost:8080**  
All API endpoints are prefixed with `/foodorder/api/v1`

### 5 — Verify

```bash
curl http://localhost:8080/foodorder/api/v1/foods
```

A JSON array is returned (empty on a fresh database).

---

## ⚙️ Configuration

All runtime settings live in `src/main/resources/application-dev.properties`:

```properties
# ── Server ──────────────────────────────────────────────────────────────────
server.port=8080
spring.application.name=FoodOrder
server.servlet.context-path=/foodorder

# ── Database ─────────────────────────────────────────────────────────────────
spring.datasource.url=jdbc:mysql://localhost:3306/food_ordering_db\
  ?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

# ── JPA / Hibernate ──────────────────────────────────────────────────────────
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# ── JWT ──────────────────────────────────────────────────────────────────────
# Must be a Base64-encoded string of at least 32 bytes (256 bits for HS256)
jwt.secret=Zm9vZE9yZGVyU2VjcmV0S2V5Rm9yQ01KREJhdGNoMTEyMTEzU2VjdXJlS2V5
jwt.expiration=86400000   # 24 hours in milliseconds
```

> ⚠️ **Important:** Use a strong, unique `jwt.secret` before production deployment.

### Active profile

`application.properties` activates the dev profile:

```properties
spring.profiles.active=dev
```

---

## 🗄️ Database

### Relationships

```
UserEntity  ──1:1──  CartEntity  ──1:N──  CartItemEntity ──N:1──  FoodItemEntity
                                                                         │
UserEntity  ──1:N──  OrderEntity ──1:N──  OrderItemEntity ──N:1───────────┘
                          │
                     1:1  │
                    PaymentEntity

CategoryEntity ──1:N──  FoodItemEntity
```

### Enum values

| Enum | Values |
|------|--------|
| `Role` | `ADMIN`, `CUSTOMER` |
| `FoodItemStatus` | `AVAILABLE`, `OUT_OF_STOCK` |
| `OrderStatus` | `PLACED`, `PREPARING`, `DELIVERED`, `CANCELLED` |
| `PaymentStatus` | `PENDING`, `COMPLETED`, `FAILED` |

### Order status flow

```
PLACED  ──→  PREPARING  ──→  DELIVERED
  │                               
  └──────────────────→  CANCELLED
```

---

## 📡 API Endpoints

> **Base URL:** `http://localhost:8080/foodorder/api/v1`  
> **Auth header:** `Authorization: Bearer <JWT_TOKEN>`

---

### 🔐 Authentication

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `POST` | `/auth/signup` | Public | Register a new customer account |
| `POST` | `/auth/login` | Public | Sign in and receive a JWT token |

#### POST `/auth/signup`

**Request body:**
```json
{
  "name":     "John Doe",
  "email":    "john@example.com",
  "password": "secret123",
  "phone":    "+94 77 123 4567",
  "address":  "123 Main St, Colombo"
}
```

**Response `201`:**
```json
{
  "token":  "eyJhbGciOiJIUzI1NiJ9...",
  "userId": "USR-a1b2c3d4",
  "email":  "john@example.com",
  "name":   "John Doe",
  "role":   "CUSTOMER"
}
```

#### POST `/auth/login`

**Request body:**
```json
{
  "email":    "john@example.com",
  "password": "secret123"
}
```

**Response `200`:** Same structure as signup response.

---

### 👤 Users

> All user endpoints require **ADMIN** role except `GET /users/me`.

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/users/me` | Any auth | Get currently logged-in user's profile |
| `GET` | `/users` | ADMIN | List all registered users |
| `GET` | `/users/{userId}` | ADMIN | Get a specific user by ID |
| `PUT` | `/users/{userId}` | ADMIN | Update a user's details |
| `DELETE` | `/users/{userId}` | ADMIN | Delete a user account |

#### GET `/users/me` — Response `200`

```json
{
  "userId":  "USR-a1b2c3d4",
  "name":    "John Doe",
  "email":   "john@example.com",
  "phone":   "+94 77 123 4567",
  "address": "123 Main St, Colombo",
  "role":    "CUSTOMER"
}
```

---

### 📂 Categories

> Read operations are **public**. Write operations require **ADMIN**.

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/categories` | Public | List all categories |
| `GET` | `/categories/{id}` | Public | Get a specific category |
| `POST` | `/categories` | ADMIN | Create a new category |
| `PUT` | `/categories/{id}` | ADMIN | Update a category |
| `DELETE` | `/categories/{id}` | ADMIN | Delete a category |

#### POST `/categories` — Request body

```json
{
  "name":        "Burgers",
  "description": "Juicy flame-grilled burgers",
  "imageUrl":    "https://example.com/burgers.jpg"
}
```

#### GET `/categories` — Response `200`

```json
[
  {
    "categoryId":    "CAT-x1y2z3",
    "name":          "Burgers",
    "description":   "Juicy flame-grilled burgers",
    "imageUrl":      "https://example.com/burgers.jpg",
    "foodItemCount": 6
  }
]
```

---

### 🍔 Food Items

> Read operations are **public**. Write operations require **ADMIN**.

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/foods` | Public | List all food items |
| `GET` | `/foods?categoryId={id}` | Public | Filter by category |
| `GET` | `/foods?search={term}` | Public | Search by name |
| `GET` | `/foods/{foodItemId}` | Public | Get a specific food item |
| `POST` | `/foods` | ADMIN | Create a new food item |
| `PUT` | `/foods/{foodItemId}` | ADMIN | Update a food item |
| `DELETE` | `/foods/{foodItemId}` | ADMIN | Delete a food item |

#### POST `/foods` — Request body

```json
{
  "name":        "Classic Cheeseburger",
  "description": "Beef patty with cheddar, lettuce, and tomato",
  "price":       850.00,
  "imageUrl":    "https://example.com/cheeseburger.jpg",
  "status":      "AVAILABLE",
  "categoryId":  "CAT-x1y2z3"
}
```

#### GET `/foods` — Response `200`

```json
[
  {
    "foodItemId":   "FOOD-ab12cd34",
    "name":         "Classic Cheeseburger",
    "description":  "Beef patty with cheddar, lettuce, and tomato",
    "price":        850.00,
    "imageUrl":     "https://example.com/cheeseburger.jpg",
    "status":       "AVAILABLE",
    "categoryId":   "CAT-x1y2z3",
    "categoryName": "Burgers"
  }
]
```

---

### 🛒 Cart

> All cart endpoints require **authentication**.

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/cart` | Any auth | Get current user's cart |
| `POST` | `/cart/add` | Any auth | Add an item to the cart |
| `PUT` | `/cart/items/{cartItemId}?quantity={n}` | Any auth | Update item quantity |
| `DELETE` | `/cart/items/{cartItemId}` | Any auth | Remove an item from the cart |
| `DELETE` | `/cart/clear` | Any auth | Clear the entire cart |

#### POST `/cart/add` — Request body

```json
{
  "foodItemId": "FOOD-ab12cd34",
  "quantity":   2
}
```

#### GET `/cart` — Response `200`

```json
{
  "cartId":     1,
  "userId":     "USR-a1b2c3d4",
  "items": [
    {
      "id":            1,
      "foodItemId":    "FOOD-ab12cd34",
      "foodItemName":  "Classic Cheeseburger",
      "foodItemImage": "https://example.com/cheeseburger.jpg",
      "quantity":      2,
      "unitPrice":     850.00,
      "subtotal":      1700.00
    }
  ],
  "totalPrice": 1700.00,
  "totalItems": 1
}
```

---

### 📦 Orders

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `POST` | `/orders` | Any auth | Place an order from the current cart |
| `GET` | `/orders/my` | Any auth | Get the logged-in user's order history |
| `GET` | `/orders/{orderId}` | Any auth | Get a specific order |
| `PUT` | `/orders/{orderId}/cancel` | Any auth | Cancel an order (PLACED or PREPARING only) |
| `GET` | `/orders` | ADMIN | List all orders in the system |
| `PUT` | `/orders/{orderId}/status?status={value}` | ADMIN | Update order status |

#### POST `/orders` — Request body

```json
{
  "deliveryAddress": "456 Beach Road, Galle"
}
```

#### GET `/orders/my` — Response `200`

```json
[
  {
    "orderId":         "ORD-ee11ff22",
    "userId":          "USR-a1b2c3d4",
    "userName":        "John Doe",
    "orderItems": [
      {
        "orderItemId":   1,
        "foodItemId":    "FOOD-ab12cd34",
        "foodItemName":  "Classic Cheeseburger",
        "quantity":      2,
        "unitPrice":     850.00,
        "subtotal":      1700.00
      }
    ],
    "totalAmount":     1700.00,
    "status":          "PLACED",
    "orderDate":       "2026-05-03T14:30:00",
    "deliveryAddress": "456 Beach Road, Galle",
    "payment":         null
  }
]
```

---

### 💳 Payments

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `POST` | `/payments` | Any auth | Record a payment for an order |
| `GET` | `/payments/order/{orderId}` | Any auth | Get payment details for an order |

#### POST `/payments` — Request body

```json
{
  "orderId":       "ORD-ee11ff22",
  "transactionId": "TXN-1714735800000"
}
```

#### Response `200`

```json
{
  "paymentId":     "PAY-cc33dd44",
  "orderId":       "ORD-ee11ff22",
  "amount":        1700.00,
  "status":        "COMPLETED",
  "paymentDate":   "2026-05-03T14:35:00",
  "transactionId": "TXN-1714735800000"
}
```

---

## 🔒 Authentication and Security

### JWT Flow

```
1. Client sends POST /auth/login with email + password
2. Server validates credentials via Spring Security AuthenticationManager
3. Server generates a signed JWT (HS256) containing: email + expiry
4. Client stores the token and attaches it to every subsequent request:
      Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
5. JwtAuthFilter intercepts every request, extracts the token,
   validates the signature, and loads the user into SecurityContext
6. Controllers see the authenticated principal via @AuthenticationPrincipal
```

### Role Access

| URL Pattern | ADMIN | CUSTOMER | Public |
|-------------|:-----:|:--------:|:------:|
| `POST /auth/**` | ✅ | ✅ | ✅ |
| `GET /foods/**` | ✅ | ✅ | ✅ |
| `GET /categories/**` | ✅ | ✅ | ✅ |
| `POST/PUT/DELETE /foods/**` | ✅ | ❌ | ❌ |
| `POST/PUT/DELETE /categories/**` | ✅ | ❌ | ❌ |
| `/users/**` (all) | ✅ | ❌ (own) | ❌ |
| `/cart/**` | ✅ | ✅ | ❌ |
| `/orders/**` | ✅ | ✅ (own) | ❌ |
| `/payments/**` | ✅ | ✅ | ❌ |

### Token details

| Property | Value |
|----------|-------|
| Algorithm | HMAC-SHA256 (HS256) |
| Expiration | 24 hours (configurable via `jwt.expiration`) |
| Payload | `sub` (email), `iat`, `exp` |

---

## 🛡️ Default Admin

On **first startup**, `DataInitializer` creates an admin user if one does not already exist:

| Field | Value |
|-------|-------|
| **Email** | `admin@foodorder.lk` |
| **Password** | `Admin@1234` |
| **Role** | `ADMIN` |

> ⚠️ **Change the default password** immediately after your first login in a production environment.

You will see this in the startup log:

```
=====================================================
  DEFAULT ADMIN ACCOUNT CREATED
  Email    : admin@foodorder.lk
  Password : Admin@1234
  IMPORTANT: Change this password after first login!
=====================================================
```

---

## ❌ Error Handling

The API returns errors in a common JSON format:

```json
{
  "status":    404,
  "message":   "Food item not found with id: FOOD-xyz",
  "timestamp": "2026-05-03T14:30:00"
}
```

### Validation errors

When a request body fails `@Valid` checks, field-level errors are returned:

```json
{
  "status":  400,
  "error":   "Validation Failed",
  "fields": {
    "email":    "must be a well-formed email address",
    "password": "size must be between 6 and 2147483647"
  }
}
```

### HTTP status codes used

| Code | Meaning |
|------|---------|
| `200` | Success |
| `201` | Resource created |
| `400` | Bad request / validation error |
| `401` | Unauthenticated (no token or invalid token) |
| `403` | Authorised but insufficient role |
| `404` | Resource not found |
| `409` | Conflict (e.g. email already registered) |
| `500` | Unexpected server error |

---

## 📝 Logging

Logs are printed in the console. The application uses `DEBUG` level in the dev profile for development details:

```
2026-05-03 14:30:01 INFO  AuthServiceImpl - User logged in: john@example.com
2026-05-03 14:30:45 INFO  OrderServiceImpl - Order placed: ORD-ee11ff22 for user: USR-a1b2c3d4
2026-05-03 14:31:10 INFO  PaymentServiceImpl - Payment processed: PAY-cc33dd44 for order: ORD-ee11ff22
```

---

## 📄 License

This project is developed for educational purposes as part of the **CMJD — Comprehensive Master Java Developer** programme at **IJSE**, Sri Lanka.

---

<div align="center">
  Developed by Jalina Sandaru
</div>