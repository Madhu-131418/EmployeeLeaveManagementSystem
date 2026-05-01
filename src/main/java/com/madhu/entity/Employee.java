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

    // many employees to one department
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // one employee to many leave requests
    @OneToMany(mappedBy = "employee")
    private List<LeaveRequest> leaveRequests;

    // one employee to many leave balances
    @OneToMany(mappedBy = "employee")
    private List<LeaveBalance> leaveBalances;
}