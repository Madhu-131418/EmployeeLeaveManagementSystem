package com.madhu.requestdto;

import lombok.Data;
import java.time.LocalDate;

import com.madhu.util.EmploymentStatus;

@Data
public class EmployeeRequestDto {

    private Integer employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private String designation;
    private LocalDate joiningDate;
    private EmploymentStatus employmentStatus;
    private Long departmentId;
}