##📌 1. Project Title
Employee Leave Management System
A backend REST API application built using Spring Boot to manage employees, departments, leave requests, and approvals.
------------------------------------------------------------------------------------
📌 2. Project Overview
This project is designed to automate and manage employee leave processes in an organization.
It allows:
Employee management
Department management
Leave request submission
Leave approval/rejection
Leave balance tracking
It follows a layered architecture (Controller → Service → Repository) using Spring Boot best practices.
------------------------------------------------------------------------------------
📌 3. Tech Stack
Java 17
Spring Boot 3.x
Spring Data JPA
Hibernate
MySQL Database
Maven
REST APIs
Postman & Swagger (for testing)
-------------------------------------------------------------------------------------
📌 4. Features
👨‍💼 Employee Module
  Add employee
  Update employee
  Get employee details
  Assign department
🏢 Department Module
  Create department
  View all departments
  Assign employees to department
🏖 Leave Module
  Apply for leave
  Approve/Reject leave
  Track leave status
📊 Leave Balance Module
  Maintain leave balance per employee
  Auto deduction after approval
---------------------------------------------------------------------------------------
📌 5. Architecture

This project follows a 3-layer architecture:
        Controller Layer → Service Layer → Repository Layer
Flow:
      Client (Postman / Browser)
        ↓
      Controller (API Layer)
        ↓
      Service (Business Logic)
        ↓
      Repository (Database Layer)
        ↓
      MySQL Database
---------------------------------------------------------------------------------------
📌 6. Project Structure
com.madhu
│
├── controller
├── service
├── serviceimpl
├── repository
├── entity
├── requestdto
├── responsedto
├── exception
├── util
└── EmployeeLeaveManagementApplication.java
----------------------------------------------------------------------------------------
📌 7. Database Configuration
application.properties :
spring.application.name=EmployeeLeaveManagementSystem
spring.datasource.url=jdbc:mysql://localhost:3306/project2
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
-----------------------------------------------------------------------------------------
📌 8. Environment Variables
This project uses environment variables for security.
Required Variables:
      DB_USERNAME = your_mysql_username
      DB_PASSWORD = your_mysql_password
-----------------------------------------------------------------------------------------
📌 9. How to Run the Project
Step 1: Clone Repository
      git clone https://github.com/your-username/EmployeeLeaveManagementSystem.git
Step 2: Import into STS / Eclipse
      File → Import → Maven Project
Step 3: Set Environment Variables
      DB_USERNAME
      DB_PASSWORD
Step 4: Run Application
      mvn spring-boot:run
------------------------------------------------------------------------------------------
📌 10. API Base URL
      http://localhost:8080/leaveapi
------------------------------------------------------------------------------------------
📌 11. Sample API Endpoints
Employee APIs
    GET    /employees
    POST   /employees
    GET    /employees/{id}
    PUT    /employees/{id}
DELETE /employees/{id}
    Leave APIs
    POST   /leaves/apply
    GET    /leaves/status/{id}
    PUT    /leaves/approve/{id}
    PUT    /leaves/reject/{id}
Department APIs
    POST   /departments
    GET    /departments
-------------------------------------------------------------------------------------------
📌 12. Exception Handling
Custom exceptions are implemented : 
    ResourceNotFoundException
    DuplicateResourceException
    InvalidLeaveRequestException
    GlobalExceptionHandler (Centralized error handling)
-------------------------------------------------------------------------------------------
📌 13. Key Learnings
    Spring Boot REST API development
    Layered architecture design
    JPA relationships (OneToMany, ManyToOne)
    Exception handling in Spring Boot
    DTO pattern implementation
    Environment variable configuration
    MySQL integration
-------------------------------------------------------------------------------------------
📌 14. Future Enhancements
    JWT Authentication (Login system)
    Role-based access (Admin / Employee)
    Swagger API documentation
    Email notifications for leave approval
-------------------------------------------------------------------------------------------
📌 15. Author
Madhu
Java Backend Developer


