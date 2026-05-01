package com.madhu.responsedto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeResponseDto {

    private Long employeeId;
    private Integer employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private String designation;
    private LocalDate joiningDate;
    private String employmentStatus;
    private Long departmentId;
    private String departmentName;
}