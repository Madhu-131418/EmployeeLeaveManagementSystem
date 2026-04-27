package com.madhu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "departments")
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    private Integer departmentCode;
    private String departmentName;
    private String location;

    // ONE DEPARTMENT → MANY EMPLOYEES
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}