# Objective
We will create:
- A PostgreSQL database
- A Spring Boot application
- REST endpoints to store and retrieve data from the PostgreSQL database


## 1. Setting up the PostgreSQL Database

##### step 1: Open pgAdmin and create a database and user:

```sql
CREATE DATABASE mydb;
CREATE USER user WITH ENCRYPTED PASSWORD 'secret';
```

#### step 2: Make sure PostgreSQL is running on port 5432 (default)
Server Name: PostgreSQL 17
Host name/address: localhost
Port: 5432
Database: mydb
User: user
Password: secret
Role: pg_database_owner


Connection Parameters:
SSL mode: prefer (Try SSL first, fall back to plain text)
Connection timeout: 10 seconds


**Note**:
- SSL (Secure Sockets Layer) is a security protocol that encrypts communication between two systems (browiser <-> server or
spring boot <-> PostgreSQL).
- TSL(Transport Layer Security) is the modern replacement
- Without SSL
  - Data is sent in plain text
  - Anyone on the same network could intercept passwords, queries or results
- With SSL
  - All communication is encrypted
  - Attackers see only scrambled data

## 2. Configuring ```application.properties```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=user
spring.datasource.password=secret
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update   # (dev only, use Flyway/Liquibase in prod)
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```

Note: ```spring.jpa.hibernate.ddl-auto=update```
- let's Hibernate create/update tables automatically



