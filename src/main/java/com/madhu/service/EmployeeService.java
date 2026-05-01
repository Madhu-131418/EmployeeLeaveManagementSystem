package com.madhu.service;


import com.madhu.responsedto.*;
import com.madhu.requestdto.*;
import java.util.List;

public interface EmployeeService {

    EmployeeResponseDto registerEmployee(EmployeeRequestDto dto);
    EmployeeResponseDto fetchEmployeeById(Long id);
    List<EmployeeResponseDto> fetchAllEmployees();
    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto);
    void removeEmployee(Long id);
    List<EmployeeResponseDto> fetchEmployeesByDepartment(Long deptId);
    List<LeaveRequestResponseDto> getEmployeeLeaveHistory(Long id);
}