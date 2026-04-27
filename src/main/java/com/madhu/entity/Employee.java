package com.madhu.entity;

import com.madhu.util.EmploymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private Integer employeeCode;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private Long phoneNumber;
    private String designation;
    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    // MANY EMPLOYEES → ONE DEPARTMENT
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // ONE EMPLOYEE → MANY LEAVE REQUESTS
    @OneToMany(mappedBy = "employee")
    private List<LeaveRequest> leaveRequests;

    // ONE EMPLOYEE → MANY LEAVE BALANCES
    @OneToMany(mappedBy = "employee")
    private List<LeaveBalance> leaveBalances;
}