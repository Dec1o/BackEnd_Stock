SpringBoot Inventory System, developed by: Décio Carvalho Faria.

<img width="1919" height="990" alt="spring" src="https://github.com/user-attachments/assets/0a78dbca-d87a-4b1f-93c6-6cf0dd493f9e" />


# 📁 Summary

● Environment Preparation

● System Operation

● Monolithic Design Pattern

● MVC Architecture

● Entity Relationship (ER) Model

● Authentication System

● Route List



# 🛠 1. Environment Preparation

# Requirements:

● Java 17

● Maven

● MySQL 8+

● IDE (e.g., IntelliJ, Eclipse, VS Code)


# Steps:

1. Clone the repository

2. Create a MySQL database called "stock"

3. Configure application.properties with the database credentials

4. Run: mvn clean install , then mvn spring-boot:run

5. Start the project via the IDE or with: mvn spring-boot:run



# 📟 2. System Operation


# 2.1 Authentication and Registration:

● Admin user registration (without a company) via /register

● Login with token generation via /auth/login

● Token refresh via /auth/refresh

● Passwords saved with BCrypt


# 2.2 Users

● Admins can create, edit, list, and delete users within their own company

● Regular users are not allowed to create new users


# 2.3 Companies

● Only administrators can create their own company

● Each admin can have only one company

● Admins can edit or delete their own company


# 2.4 Categories

● CRUD of categories linked to the logged-in user's company


# 2.5 Products

● CRUD of products linked to a category (and therefore, to a company)



# 🧱 3. Monolithic Design Pattern:
This system adopts the monolithic design pattern, in which the entire application (business logic, control layer, persistence, and configuration) resides within a single project and codebase.


# - Project Organization:

├── controller

├── service

├── model

├── repository

├── dto

├── filter

├── config

└── EstoqueApplication.java



# 🤭 4. MVC Architecture (Model-View-Controller)

# - Model (JPA entities)

● User.java

● Company.java

● Category.java

● Product.java


# - Controller (REST input layer)

● AuthController.java

● RegisterController.java

● UserController.java

● CompanyController.java

● CategoryController.java

● ProductController.java


# - Service (business logic)

● AuthService.java

● UserService.java

● CompanyService.java

● CategoryService.java

● ProductService.java


# - Repository (database access)

● UserRepository.java

● CompanyRepository.java

● CategoryRepository.java

● ProductRepository.java


# - DTOs (data transport)

● AuthRequestDTO.java

● AuthResponseDTO.java

● UserDTO.java

● CompanyDTO.java

● CategoryDTO.java

● ProductDTO.java


# - Settings

● JwtUtil.java

● JwtFilter.java

● SecurityConfig.java



# 📂 5. Entity-Relationship (ER) Model

● User → belongs to a Company (ManyToOne)

● Company → has many Users and Categories (OneToMany)

● Category → belongs to a Company (ManyToOne)

● Product → belongs to a Category (ManyToOne)



# 🔐 6. JWT Authentication System
The system uses JWT (JSON Web Token) for route authentication and authorization:


# Components:

● AuthRequestDTO.java → receives email/password

● AuthResponseDTO.java → returns tokens

● AuthController.java → /login and /refresh

● AuthService.java → Logic for authentication and token generation

● JwtUtil.java → JWT generation, extraction, and verification

● JwtFilter.java → Intercepts requests to validate tokens

● SecurityConfig.java → Defines API security policies


# Flow:

# - POST /auth/login

→ AuthController calls AuthService

→ Validates email/password → Generates JWT

→ Returns accessToken and refreshToken


# - Requests with JWT

→ Client sends token in the Authorization Header

→ JwtFilter validates and authenticates

# - POST /auth/refresh

→ Generates a new accessToken from refreshToken



# 🌐 7. Route List

# Authentication:

● POST /auth/login

● POST /auth/refresh


# Usuários:

● POST /register

● GET /users

● GET /users/{id}

● POST /users

● PUT /users/{id}

● DELETE /users/{id}


# Empresas:

● POST /companies

● GET /companies

● PUT /companies/{id}

● DELETE /companies/{id}


# Categorias:

● GET /categories

● GET /categories/{id}

● POST /categories

● PUT /categories/{id}

● DELETE /categories/{id}


# Produtos:

● GET /products

● GET /products/{id}

● POST /products

● PUT /products/{id}

● DELETE /products/{id}



# All protected routes require JWT token in the header:

Authorization: Bearer <your-token>
