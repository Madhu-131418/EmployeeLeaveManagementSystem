package com.madhu.responsedto;


import lombok.Data;

@Data
public class DepartmentResponseDto {

    private Long departmentId;
    private Integer departmentCode;
    private String departmentName;
    private String location;
}
