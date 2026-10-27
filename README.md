# Product Service (`product-service-group-2`)

Microservice responsible for managing the product catalog and stock levels within the e-commerce system.

---

## 1. Responsibilities & System Role
- **Catalog Management:** Create, retrieve, and delete products as well as product categories.
- **Inventory & Stock Management:** Atomic and transactional stock reduction (`decreaseStock`) when orders are placed by `order-service`.
- **Security:** Validates incoming JWT tokens against `auth-service` for protected endpoints.

---

## 2. Tech Stack
- **Language & Framework:** Java 17, Spring Boot 4
- **Persistence:** Spring Data JPA, Hibernate
- **Mapping & Boilerplate:** MapStruct, Lombok
- **Security:** Spring Security (OAuth2 Resource Server, JWT)
- **API Documentation:** SpringDoc OpenAPI / Swagger UI
- **Build Tool:** Maven (via wrapper `./mvnw`)

---

## 3. Database
- **Local / Testing:** H2 In-Memory Database (`jdbc:h2:mem:testdb`). No local database installation required.
- **Production / Cloud (Render):** Configurable via environment variables to an external database (e.g., PostgreSQL or MySQL).

---

## 4. Port & Environment Variables

Default port: **`5002`**

| Environment Variable | Description | Default Value (Local) |
| :--- | :--- | :--- |
| `SERVER_PORT` | Port the microservice runs on | `5002` |
| `DB_URL` | JDBC database connection string | `jdbc:h2:mem:testdb` |
| `DB_USERNAME` | Database username | `sa` |
| `DB_PASSWORD` | Database password | *(empty)* |
| `DB_DRIVER` | JDBC driver class name | `org.h2.Driver` |
| `DB_PLATFORM` | Hibernate dialect | `org.hibernate.dialect.H2Dialect` |
| `AUTH_SERVER` | URL to Auth Service for token validation | `http://localhost:8080` |

---

## 5. Getting Started

Ensure you have Java 17 (or later) installed.

### Run on Linux / macOS:
```bash
./mvnw spring-boot:run
```

### Run on Windows:
```cmd
mvnw.cmd spring-boot:run
```

Once started, the service is accessible at: `http://localhost:5002`

---

## 6. Documentation & Javadoc

- **Swagger UI (Interactive API docs):** `http://localhost:5002/swagger-ui.html`
- **OpenAPI JSON specification:** `http://localhost:5002/v3/api-docs`
- **Generate Javadoc HTML report:**
  ```bash
  ./mvnw javadoc:javadoc
  ```
  Open the generated report in your browser:
  ```bash
  xdg-open target/reports/apidocs/index.html
  ```

---

## 7. Related Services in the System

This project is part of a distributed microservice architecture:

- [Auth Service](https://github.com/Jimmy-Grafstrom/auth-service-group-2) – User management and JWT issuance (Port: 8080)
- [Order Service](https://github.com/Jimmy-Grafstrom/order-service-group-2) – Order processing and order history
- [API Gateway](https://github.com/Jimmy-Grafstrom/gateway-group-2) – Routing and centralized gateway entrypoint
- [Frontend](https://github.com/Jimmy-Grafstrom/frontend-group-2) – Web client (built with `npm install` and started with `npm run dev`)
