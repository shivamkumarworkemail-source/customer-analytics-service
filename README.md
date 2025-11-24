# DevOn Bank – Customer Analytics Service

A Spring Boot based microservice that provides customer insights, transaction analytics,
and monthly statements for banking applications.

---

## Features
- Customer lookup and profiling
- Transaction analytics
- Customer activity insights
- Monthly statements with balance calculation

---

## Tech Stack
- Java 17
- Spring Boot 3.x
- JDBC Template
- Oracle Database
- JUnit 5 & Mockito

---

## Architecture Overview
This service follows layered architecture:
Controller → Service → Repository → Database

---

## Running the Project

### Prerequisites
- JDK 17
- Maven 3+
- Oracle DB (schema provided below)

### Start Application
```bash
mvn clean install
mvn spring-boot:run
