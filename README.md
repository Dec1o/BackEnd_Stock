# Sistema de Estoque SpringBoot

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1+-6DB33F?logo=spring)
![Java](https://img.shields.io/badge/Java-17-007396?logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8+-4479A1?logo=mysql)

Sistema de gerenciamento de estoque com autenticação JWT, controle de permissões e CRUD completo para produtos, categorias, usuários e empresas.

## 📋 Sumário
- [Introdução](#-introdução)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração](#-configuração)
- [Arquitetura](#-arquitetura)
- [Funcionalidades](#-funcionalidades)
- [Rotas da API](#-rotas-da-api)
- [Modelo ER](#-modelo-entidade-relacionamento)
- [Autenticação](#-sistema-de-autenticação-jwt)

## 🌟 Introdução
Sistema monolítico em Spring Boot com:
- Autenticação JWT com refresh token
- Permissionamento por tipo de usuário (admin/comum)
- Criptografia de senhas com BCrypt
- Gerenciamento completo de:
  - Usuários
  - Empresas
  - Categorias
  - Produtos

## 🛠️ Pré-requisitos
- Java 17+
- Maven
- MySQL 8+
- IDE (IntelliJ/Eclipse/VS Code)

## ⚙️ Configuração
```bash
# 1. Clone o repositório
git clone <repo-url>

# 2. Crie o banco de dados
CREATE DATABASE estoque;

# 3. Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/estoque
spring.datasource.username=seu_user
spring.datasource.password=sua_senha

# 4. Execute
mvn clean install
mvn spring-boot:run

