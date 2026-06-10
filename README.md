# Parking Management System

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/technologies/downloads/#java21)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-green.svg)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/Database-MSSQL-red.svg)](https://www.microsoft.com/en-us/sql-server/)

This is a partially completed **Parking Management System** developed for the academic course **HSF302 (SU26)**. The primary objective of this project is to implement only the **Create Parking Session** feature, as required by the assignment. It utilizes **Spring Boot 4.0.6**, **Java 21**, and **Spring Web MVC** with a robust relational database schema.

---

## 🎯 Repository Purpose

This workspace is set up to address several development and integration needs:

- **🔄 Change Tracking & Rollback**: Keep full history of incremental updates and fallback to previous working versions if something breaks.
- **💻 Cross-Machine Portability**: Work seamlessly on different machines (desktop, laptop, or lab computers) without manual file-syncing.
- **🤖 Native AI Collaboration**: Allow AI coding agents to read and analyze the codebase directly in its native context without sending files manually.

---

## 🤖 AI Assistant Setup Prompt

Copy and run this prompt when initializing an AI agent or coding session:

> Take a look at this project <https://github.com/minhvc/SU26_HSF302_SE1909_ASM03_SE203168_MinhVC.git>, read the codebase, and set up the project. Keep in mind that the primary IDE used is IntelliJ IDEA and the relational database is Microsoft SQL Server (MSSQL).

---

## 💻 Environment Setup

To keep local development environments consistent, the project is configured with:

- **IDE**: IntelliJ IDEA (with Lombok annotation processor enabled)
- **Database**: Microsoft SQL Server (MSSQL)
- **Build System**: Apache Maven (using Maven Wrapper `mvnw` / `mvnw.cmd`)

---

## 📖 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Database Schema & Domain Models](#-database-schema--domain-models)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Configuration](#-configuration)
- [Installation & Setup](#-installation--setup)
- [Usage & Local Run](#-usage--local-run)
- [Troubleshooting](#-troubleshooting)
- [License](#-license)

---

## 🌟 Key Features

- **🚗 Parking Space Management**: Model structures across buildings, floors, zones, and individual slots (`ParkingSlot`).
- **📅 Session Booking & Reservations**: Manage real-time parking entries (`ParkingSessions`) and advanced slot pre-booking (`Reservation`).
- **💳 Pricing & Payments**: Calculate fees automatically with configurable `PricingPolicy` and track financial records (`Payment`).
- **🚨 Incident Reporting**: Monitor and report slot violations, accidents, or physical damages (`IncidentReport`).
- **👤 Multi-role User Accounts**: Separate access permissions with linked `User` and `Role` models.

---

## 🛠️ Tech Stack

- **Core Framework**: Spring Boot 4.0.6
- **Language**: Java 21
- **Presentation**: Spring Web MVC, Thymeleaf server-side templates
- **ORM & Data Access**: Spring Data JPA & Hibernate
- **Database**: MSSQL (supported by `mssql-jdbc` driver, `@Nationalized` and `@ColumnDefault` annotations)
- **Developer Tools**: Lombok to reduce boilerplate code

---

## 🗄️ Database Schema & Domain Models

Entities map cleanly to a standard relational schema:

- **`User` / `Role`**: Authentication and access roles.
- **`Building` -> `Floor` -> `Zone` -> `ParkingSlot`**: The spatial structure of the parking spaces.
- **`VehicleType` -> `Vehicle`**: Tracks customer vehicles.
- **`Reservation`**: Pre-booking transactions.
- **`ParkingSessions`**: Real-time entry/exit timestamps and session status.
- **`PricingPolicy` & `Payment`**: Billing configurations and transaction records.
- **`IncidentReport`**: Logs damages or parking violations.
- **`ParkingPrediction`**: Machine learning/heuristic target structures for future demand prediction.

---

## 📂 Project Structure

```text
SU26_HSF302_SE1909_ASM03_SE203168_MinhVC
├── .mvn/                       # Maven wrapper configuration
├── src/
│   ├── main/
│   │   ├── java/org/minhvc/springwebmvc/parkingmanagement/
│   │   │   ├── Su26Hsf302...Application.java  # Main Application Entry
│   │   │   ├── ServletInitializer.java        # Tomcat WAR compatibility
│   │   │   ├── controllers/                   # Spring Web MVC Controllers
│   │   │   ├── entities/                      # JPA / Hibernate Domain Entities
│   │   │   ├── repositories/                  # Spring Data Repositories
│   │   │   └── services/                      # Services (IParkingSessionsService, etc.)
│   │   └── resources/
│   │       ├── templates/                     # Thymeleaf template views
│   │       └── application.properties         # Database & app settings
│   └── test/                                  # Integration & Unit Tests
├── mvnw / mvnw.cmd             # Platform-independent Maven build wrappers
├── Query.sql                   # Database setup and seed SQL commands
└── AGENTS.md                   # Development workflow instructions for AI agents
```

---

## ⚙️ Prerequisites

Ensure your system has the following components installed:

1. **Java Development Kit (JDK) 21**
2. **Microsoft SQL Server (MSSQL)**
3. **IntelliJ IDEA** (or an IDE of choice with Lombok plugin installed)

---

## 🔧 Configuration

Update the credentials in `src/main/resources/application.properties` to connect to your local database:

### Microsoft SQL Server

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ParkingManagementSystem;encrypt=true;trustServerCertificate=true
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
```

### H2 In-Memory Database (Fallback)

If you want to run the application without an active MSSQL instance, use H2 for testing:

```properties
spring.datasource.url=jdbc:h2:mem:parkingdb;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

---

## 🚀 Installation & Setup

1. **Clone the Repo**:

   ```bash
   git clone https://github.com/minhvc/SU26_HSF302_SE1909_ASM03_SE203168_MinhVC.git
   cd SU26_HSF302_SE1909_ASM03_SE203168_MinhVC
   ```

2. **Create and Seed Database**:
   - Create a database named `ParkingManagementSystem` in MSSQL.
   - Run the script located in `Query.sql` to generate the initial structure and mock data.
3. **Build Application**:
   - Windows: `.\mvnw.cmd clean package`
   - Unix/macOS: `./mvnw clean package`

---

## 💻 Usage & Local Run

### Start the Application

Execute the Spring Boot plugin goal:

- Windows: `.\mvnw.cmd spring-boot:run`
- Unix/macOS: `./mvnw spring-boot:run`

Access the web interface at:
👉 **[http://localhost:8080/CreateParkingSessions/index](http://localhost:8080/CreateParkingSessions/index)**

### Run Unit/Integration Tests

```bash
./mvnw test
```

### Remote Debugging

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
```

---

## 🔍 Troubleshooting

- **Lombok Annotation Compilation Errors**: Verify annotation processing is enabled under **Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors** in IntelliJ.
- **SQL Server Connection Timeout**: Confirm MSSQL Server is running and TCP/IP protocol is enabled inside SQL Server Configuration Manager (under port 1433).
- **Thymeleaf Template Views Not Found**: Casing matters on case-sensitive systems (such as Linux environments). Ensure controller paths align exactly with subdirectories under `src/main/resources/templates`.

---

## 📄 License

This project is licensed under the MIT License.
