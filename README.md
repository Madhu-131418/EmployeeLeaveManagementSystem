# 🗓️ Employee Leave Management System

A production-ready **RESTful API** built with **Spring Boot 3** for managing employee leave requests across departments. It supports the full leave lifecycle — from applying and tracking leave to approval/rejection workflows — backed by MySQL and documented with Swagger UI.

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Data Models](#data-models)
- [Exception Handling](#exception-handling)
- [Swagger Documentation](#swagger-documentation)
- [Author](#author)

---

## ✨ Features

- **Employee Management** — Register, update, delete, and fetch employees with department association
- **Department Management** — Create and manage organizational departments
- **Leave Type Management** — Configure different leave types (e.g., Sick, Casual, Annual)
- **Leave Balance Tracking** — Year-wise leave balance management per employee per leave type
- **Leave Request Lifecycle** — Apply, approve, reject, and cancel leave requests with manager comments
- **Smart Validations** — Date range checks, insufficient balance detection, duplicate prevention
- **Global Exception Handling** — Structured error responses for all failure scenarios
- **Swagger / OpenAPI UI** — Interactive API documentation out of the box

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Persistence | Spring Data JPA + Hibernate |
| Database | MySQL |
| Validation | Spring Boot Starter Validation |
| API Docs | SpringDoc OpenAPI (Swagger UI) 2.5.0 |
| Build Tool | Maven |
| Boilerplate Reduction | Lombok |
| Dev Tooling | Spring Boot DevTools |

---

## 🏛️ Architecture

The application follows a standard **layered architecture**:

```
Client (Postman / Swagger UI)
        │
        ▼
   Controller Layer        ← REST endpoints, HTTP request/response mapping
        │
        ▼
   Service Interface       ← Business logic contracts
        │
        ▼
   Service Impl Layer      ← Core business logic, validations, transactions
        │
        ▼
   Repository Layer        ← Spring Data JPA repositories
        │
        ▼
   Entity / DB Layer       ← JPA entities mapped to MySQL tables
```

**DTOs** (Request & Response) are used throughout to decouple API contracts from internal entities.

---

## 📁 Project Structure

```
src/main/java/com/madhu/
│
├── EmployeeLeaveManagementApplication.java   # Main entry point
│
├── controller/                               # REST Controllers
│   ├── DepartmentController.java
│   ├── EmployeeController.java
│   ├── LeaveBalanceController.java
│   ├── LeaveRequestController.java
│   └── LeaveTypeController.java
│
├── entity/                                   # JPA Entities
│   ├── Department.java
│   ├── Employee.java
│   ├── LeaveBalance.java
│   ├── LeaveRequest.java
│   └── LeaveType.java
│
├── repository/                               # Spring Data JPA Repositories
│   ├── DepartmentRepository.java
│   ├── EmployeeRepository.java
│   ├── LeaveBalanceRepository.java
│   ├── LeaveRequestRepository.java
│   └── LeaveTypeRepository.java
│
├── service/                                  # Service Interfaces
│   ├── DepartmentService.java
│   ├── EmployeeService.java
│   ├── LeaveBalanceService.java
│   ├── LeaveRequestService.java
│   └── LeaveTypeService.java
│
├── serviceimpl/                              # Service Implementations
│   ├── DepartmentServiceImpl.java
│   ├── EmployeeServiceImpl.java
│   ├── LeaveBalanceServiceImpl.java
│   ├── LeaveRequestServiceImpl.java
│   └── LeaveTypeServiceImpl.java
│
├── requestdto/                               # Incoming Request DTOs
│   ├── DepartmentRequestDto.java
│   ├── EmployeeRequestDto.java
│   ├── LeaveBalanceRequestDto.java
│   ├── LeaveRequestRequestDto.java
│   └── LeaveTypeRequestDto.java
│
├── responsedto/                              # Outgoing Response DTOs
│   ├── DepartmentResponseDto.java
│   ├── EmployeeResponseDto.java
│   ├── ErrorResponseDto.java
│   ├── LeaveBalanceResponseDto.java
│   ├── LeaveRequestResponseDto.java
│   └── LeaveTypeResponseDto.java
│
├── exception/                                # Custom Exceptions & Global Handler
│   ├── DuplicateResourceException.java
│   ├── GlobalExceptionHandler.java
│   ├── InsufficientLeaveBalanceException.java
│   ├── InvalidLeaveRequestException.java
│   └── ResourceNotFoundException.java
│
└── util/                                     # Enums
    ├── EmploymentStatus.java                 # ACTIVE, INACTIVE, RESIGNED
    └── LeaveStatus.java                      # PENDING, APPROVED, REJECTED, CANCELLED
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.8+**
- **MySQL 8.0+**

### Installation

```bash
# Clone the repository
git clone https://github.com/<your-username>/EmployeeLeaveManagementSystem.git

# Navigate to the project directory
cd EmployeeLeaveManagementSystem
```

### Configuration

The application uses environment variables for sensitive credentials. Create a MySQL database and set the following environment variables before running:

```bash
export DB_USERNAME=your_mysql_username
export DB_PASSWORD=your_mysql_password
```

Update `src/main/resources/application.properties` if you need to change the database name or port:

```properties
spring.application.name=EmployeeLeaveManagementSystem

spring.datasource.url=jdbc:mysql://localhost:3306/project2
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.servlet.context-path=/leaveapi
```

> ⚠️ Make sure the MySQL database (`project2`) exists before starting the app. Hibernate will auto-create/update the tables on first run.

### Running the Application

```bash
# Using Maven Wrapper (recommended)
./mvnw spring-boot:run

# Or on Windows
mvnw.cmd spring-boot:run

# Or build and run the JAR
./mvnw clean package
java -jar target/EmployeeLeaveManagementSystem-0.0.1-SNAPSHOT.jar
```

The server will start at: `http://localhost:8080/leaveapi`

---

## 📡 API Endpoints

All endpoints are prefixed with `/leaveapi`.

### 👤 Employee

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/employees` | Register a new employee |
| `GET` | `/employees` | Get all employees |
| `GET` | `/employees/{id}` | Get employee by ID |
| `PUT` | `/employees/{id}` | Update employee details |
| `DELETE` | `/employees/{id}` | Delete an employee |
| `GET` | `/employees/department/{deptId}` | Get employees by department |
| `GET` | `/employees/{id}/leave-history` | Get leave history for an employee |

### 🏢 Department

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/departments` | Create a department |
| `GET` | `/departments` | Get all departments |
| `GET` | `/departments/{id}` | Get department by ID |
| `PUT` | `/departments/{id}` | Update a department |
| `DELETE` | `/departments/{id}` | Delete a department |

### 📋 Leave Type

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/leavetypes` | Create a leave type |
| `GET` | `/leavetypes` | Get all leave types |
| `GET` | `/leavetypes/{id}` | Get leave type by ID |

### 💰 Leave Balance

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/leavebalances` | Allocate leave balance |
| `GET` | `/leavebalances` | Get all leave balances |
| `GET` | `/leavebalances/{id}` | Get leave balance by ID |

### 📝 Leave Request

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/leaverequests` | Apply for leave |
| `GET` | `/leaverequests` | Get all leave requests |
| `GET` | `/leaverequests/{id}` | Get leave request by ID |
| `PUT` | `/leaverequests/{id}/approve` | Approve a leave request |
| `PUT` | `/leaverequests/{id}/reject` | Reject a leave request |
| `PUT` | `/leaverequests/{id}/cancel` | Cancel a leave request |
| `GET` | `/leaverequests/employee/{employeeId}` | Get leaves by employee |
| `GET` | `/leaverequests/status/{status}` | Get leaves by status |
| `GET` | `/leaverequests/dates?fromDate=&toDate=` | Get leaves between dates |

---

## 🗃️ Data Models

### Employee

| Field | Type | Notes |
|---|---|---|
| `employeeId` | Long | Auto-generated PK |
| `employeeCode` | Integer | Internal employee code |
| `firstName` | String | |
| `lastName` | String | |
| `email` | String | Unique |
| `phoneNumber` | Long | |
| `designation` | String | |
| `joiningDate` | LocalDate | |
| `employmentStatus` | Enum | `ACTIVE`, `INACTIVE`, `RESIGNED` |
| `department` | Department | Many-to-One |

### LeaveRequest

| Field | Type | Notes |
|---|---|---|
| `leaveRequestId` | Long | Auto-generated PK |
| `fromDate` | LocalDate | |
| `toDate` | LocalDate | |
| `numberOfDays` | int | Auto-calculated |
| `reason` | String | Required |
| `appliedDate` | LocalDate | |
| `leaveStatus` | Enum | `PENDING`, `APPROVED`, `REJECTED`, `CANCELLED` |
| `managerComments` | String | Added on approve/reject |
| `employee` | Employee | Many-to-One |
| `leaveType` | LeaveType | Many-to-One |

---

## ⚠️ Exception Handling

All exceptions are caught by `GlobalExceptionHandler` (`@RestControllerAdvice`) and return a consistent JSON error response:

```json
{
  "message": "Employee not found",
  "statusCode": 404,
  "timestamp": "2026-04-27T09:15:30"
}
```

| Exception | HTTP Status |
|---|---|
| `ResourceNotFoundException` | 404 Not Found |
| `DuplicateResourceException` | 409 Conflict |
| `InvalidLeaveRequestException` | 400 Bad Request |
| `InsufficientLeaveBalanceException` | 400 Bad Request |
| Generic `Exception` | 500 Internal Server Error |

---

## 📖 Swagger Documentation

Once the application is running, access the interactive Swagger UI at:

```
http://localhost:8080/leaveapi/swagger-ui/index.html
```

The OpenAPI JSON spec is available at:

```
http://localhost:8080/leaveapi/v3/api-docs
```

## 👨‍💻 Author

**Madhu**
- Built with passion using Spring Boot 3 & Java 17

---
