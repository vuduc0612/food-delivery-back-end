# Food Delivery Backend System

A comprehensive backend system for food delivery applications, built with Spring Boot, providing APIs for web/mobile food delivery applications.

![Food Delivery App](https://res.cloudinary.com/dyzx3woh4/image/upload/v1745229186/homepage_f8cvv0.png)


## 🚀 Installation Guide

### System Requirements

- JDK 17 or higher
- Maven 3.8 or higher
- MySQL 8.0
- Redis (configured in docker-compose)
- RabbitMQ (configured in docker-compose)
- Docker and Docker Compose (optional but recommended)

### Installation Steps

1. **Clone the project**
   ```bash
   git clone https://github.com/yourusername/food-delivery-back-end.git
   cd food-delivery-back-end
   ```

2. **Configure environment**
   - Create a `.env` file from `.env.example` template
   - Update database connection details and other necessary configurations

3. **Start dependent services with Docker**
   ```bash
   docker-compose up -d
   ```
   This command will start Redis and RabbitMQ services

4. **Build the application**
   ```bash
   mvn clean package -DskipTests
   ```

## 🚀 Usage Guide

### Starting the Application

1. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

2. **Or run the packaged JAR file**
   ```bash
   java -jar target/food-delivery-back-end-0.0.1-SNAPSHOT.jar
   ```

### API Endpoints

After startup, APIs are available at: http://localhost:8118/api/v1

API documentation (Swagger) can be accessed at: http://localhost:8118/swagger-ui.html

### API Usage Examples

1. **Register an account**
   ```bash
   curl -X POST http://localhost:8118/api/v1/auth/register \
   -H "Content-Type: application/json" \
   -d '{"email":"user@example.com","fullName":"User Name","phoneNumber":"0123456789","password":"password123"}'
   ```

2. **Login**
   ```bash
   curl -X POST http://localhost:8118/api/v1/auth/login \
   -H "Content-Type: application/json" \
   -d '{"email":"user@example.com","password":"password123"}'
   ```

3. **Get list of restaurants**
   ```bash
   curl -X GET http://localhost:8118/api/v1/restaurants \
   -H "Authorization: Bearer YOUR_TOKEN"
   ```

## ✨ Key Features

- **Authentication & Authorization**: JWT-based authentication with user, restaurant, driver, and admin roles.
- **Restaurant Management**: CRUD operations for restaurants, categories, and dishes.
- **Order Processing**: Shopping cart management, order creation and processing.
- **Payment**: VNPay payment gateway integration.
- **Location & Delivery**: Location management and calculation of delivery fees, time, and distance.
- **Statistics**: Revenue and order reports by time period.
- **Caching**: Redis caching for performance optimization.
- **Email & OTP Authentication**: Email verification and password reset.
- **API Documentation**: Automatic API documentation with Swagger/OpenAPI.

## 🛠️ Technologies Used

### Backend
- **Java 21**: Main programming language
- **Spring Boot 3.4.3**: Application development framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: ORM data operations
- **Spring Web**: RESTful API
- **Spring Cache**: Caching mechanism
- **Spring AMQP**: RabbitMQ integration for messaging

### Database & Cache
- **MySQL 8.0**: Primary database
- **Redis**: Caching and temporary data storage (cart, sessions)
- **Hibernate**: ORM framework

### Tools & Libraries
- **Maven**: Dependency management and build
- **Docker**: Application and service containerization
- **JWT**: JSON Web Token for authentication
- **ModelMapper**: DTO-Entity conversion
- **Lombok**: Boilerplate code reduction
- **Cloudinary**: Image management and storage
- **JavaMail**: Email sending
- **RabbitMQ**: Message broker for asynchronous processing

### Documentation & Testing
- **Swagger/OpenAPI**: Automatic API documentation

### DevOps & Monitoring
- **Docker Compose**: Container orchestration
- **Logging (SLF4J & Logback)**: Application logging
- **Spring Actuator**: Application health monitoring

## 📁 Project Structure

The project is organized in a module-based model with the following directory structure:

```
food-delivery-back-end/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/food_delivery_app/food_delivery_back_end/
│   │   │       ├── config/                 # Application configuration
│   │   │       ├── constant/               # Constants and enums
│   │   │       ├── modules/                # Functional modules
│   │   │       │   ├── auth/               # Authentication, login
│   │   │       │   ├── user/               # User management
│   │   │       │   ├── restaurant/         # Restaurant management
│   │   │       │   ├── dish/               # Dish management
│   │   │       │   ├── order/              # Order management
│   │   │       │   ├── cart/               # Shopping cart
│   │   │       │   ├── category/           # Dish categories
│   │   │       │   ├── driver/             # Driver management
│   │   │       │   ├── payment/            # Payment
│   │   │       │   ├── statistics/         # Statistics
│   │   │       │   ├── location/           # Location management
│   │   │       │   └── otp/                # OTP authentication
│   │   │       ├── response/               # Standardized response classes
│   │   │       ├── security/               # Security configuration
│   │   │       └── utils/                  # Utilities
│   │   └── resources/
│   │       ├── static/                     # Static resources
│   │       ├── templates/                  # Templates (mail, etc.)
│   │       └── application.yml             # Application configuration
│   └── test/                               # Test source code
├── pom.xml                                 # Maven configuration
└── docker-compose.yml                      # Docker configuration
```

Each functional module has a similar structure:
```
module/
├── controller/             # REST Controller
├── dto/                    # Data Transfer Objects
├── entity/                 # Entity classes
├── repository/             # JPA Repositories
└── service/                # Business Logic
```

**[For a detailed project structure and entity relationships, see the structure.md file](structure.md)**

## 🔧 Cache System

The project uses Redis to cache data and optimize performance:

- **Dish List Cache**: 30-minute TTL
- **Restaurant Details Cache**: 1-hour TTL
- **Revenue Statistics Cache**: 30-minute TTL
- **Shopping Cart Cache**: 1-day TTL


## 📫 Contact Information/Author

- **Developer**: Vu Huu Duc
- **Email**:vuhuuduc1206@gmail.com
- **GitHub**: https://github.com/vuduc0612
- **LinkedIn**: https://www.linkedin.com/in/vhduc/

---

