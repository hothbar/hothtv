# HothTV — Streaming Platform API

A full-stack streaming platform backend inspired by Netflix/Hulu, built with **Spring Boot 4** and **PostgreSQL**. Supports content cataloging, user subscriptions, cast/credits management, watch-progress tracking, and JWT-based authentication.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.0.1 |
| ORM | Spring Data JPA + Hibernate |
| Database | PostgreSQL |
| Migrations | Flyway 10 |
| Validation | Jakarta Bean Validation |
| Security | Spring Security + JJWT 0.12.6 |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven |
| Utilities | Lombok |

## Features

- **JWT Authentication** — Stateless login via `POST /api/auth/login`; all protected endpoints require a `Bearer` token. Passwords stored as BCrypt hashes.
- **Content Management** — CRUD for titles (movies and series), seasons, and episodes with nested resource relationships
- **Polymorphic Watchables** — Unified watchable abstraction over single-title films and individual series episodes, enforced by PostgreSQL trigger functions
- **Watch History & Progress** — Upsert-based tracking of per-user watch progress (seconds elapsed, completion status) across all content types
- **Cast & Credits** — Many-to-many cast and director associations with character names and billing order
- **Category Tagging** — Slug-based content categories with many-to-many title associations
- **Subscription Plans** — Tiered subscription plans with status lifecycle (ACTIVE → CANCELED / EXPIRED), one-active-per-user enforced at the database level
- **User Management** — User registration, retrieval, and deletion with email uniqueness constraint

## Architecture

```
src/main/java/org/hothtv/backend/
├── controller/        # REST controllers (12 resources)
├── service/           # Business logic with @Transactional boundaries
├── repository/        # Spring Data JPA repositories (16 interfaces)
├── model/             # JPA-mapped domain objects (22 models)
├── dto/               # Immutable Java record request/response DTOs
├── security/          # JWT filter, JWT service, Spring Security config
└── exceptions/        # Global @RestControllerAdvice exception handling
```

**Design highlights:**
- Stateless security — no HTTP sessions, every request carries a signed JWT
- `UserService` implements `UserDetailsService` for seamless Spring Security integration
- Clean layered architecture (Controller → Service → Repository → Model)
- Java records for concise, immutable DTOs
- `@Transactional(readOnly = true)` on read-path service methods
- `FetchType.LAZY` on associations to avoid N+1 queries
- PL/pgSQL trigger functions enforcing watchable subtype integrity at the DB level
- Composite PKs on junction tables via `@EmbeddedId`

## API Overview

| Resource | Endpoints |
|---|---|
| `/api/auth` | Login — returns JWT token |
| `/api/users` | Register and retrieve users |
| `/api/titles` | Create, list, get, delete titles |
| `/api/titles/{id}/seasons` | Manage seasons for a series |
| `/api/titles/{id}/seasons/{id}/episodes` | Manage episodes within a season |
| `/api/watchables` | Create and retrieve watchable items |
| `/api/watchhistory` | Upsert and list user watch progress |
| `/api/categories` | Category CRUD with slug support |
| `/api/titles/{id}/categories` | Assign categories to a title |
| `/api/people` | People/cast/crew directory |
| `/api/titles/{id}/credits` | Manage cast and director credits |
| `/api/plans` | Subscription tier management |
| `/api/users/{id}/subscriptions` | Manage user subscriptions |

**Public endpoints** (no token required): `POST /api/auth/login`, `POST /api/users`, Swagger UI.
All other endpoints require `Authorization: Bearer <token>`.

Full interactive docs available at `http://localhost:8081/swagger-ui.html` when running locally.

## Authentication Flow

```
POST /api/auth/login
{ "email": "user@example.com", "password": "secret" }

→ 200 OK
{ "token": "<signed JWT>" }
```

Include the token on subsequent requests:
```
Authorization: Bearer <signed JWT>
```

Token lifetime is configured via `jwt.expiration-ms` (default 24 hours). The secret is a Base64-encoded HMAC-SHA key set via `jwt.secret`.

## Database Schema

Core tables: `users`, `title`, `single_title`, `season`, `episode`, `category`, `person`

Watchable polymorphism: `watchable` (kind: SINGLE/EPISODE) → `single_watchable` | `episode_watchable`

Junction tables: `title_category`, `title_cast` (with character_name, billing_order), `title_director`

Tracking: `watch_history` (user_id + watchable_id unique), `subscription`, `subscription_plan`

Schema is version-controlled with Flyway migrations.

## Getting Started

**Prerequisites:** Java 17+, PostgreSQL 14+, Maven 3.8+

```bash
# Create the database
psql -U postgres -c "CREATE DATABASE hothtv;"
psql -U postgres -c "CREATE USER hothtv_user WITH PASSWORD 'yourpassword';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE hothtv TO hothtv_user;"
```

Configure `backend/src/main/resources/application.properties`:
```properties
spring.datasource.password=yourpassword

# Generate a secure Base64 key for production — the default is for dev only
jwt.secret=<base64-encoded-256-bit-key>
jwt.expiration-ms=86400000
```

```bash
# Run the app (Flyway migrations apply automatically)
cd backend
./mvnw spring-boot:run
```

Server starts on `http://localhost:8081`.

## Running Tests

```bash
cd backend
./mvnw test
```

## Roadmap

- [ ] **Role-based access control** — ADMIN vs USER roles; restrict content management endpoints to admins
- [ ] **Token refresh** — `POST /api/auth/refresh` to extend sessions without re-login
- [ ] **Frontend** — React/Next.js client consuming this API
- [ ] **Search & filtering** — full-text title search, filter by category/cast/year
- [ ] **Recommendations** — watch-history-based content suggestions
- [ ] **Password reset** — email-based reset flow
