package com.madhu.service;

import com.madhu.responsedto.*;
import com.madhu.requestdto.*;
import java.util.List;

public interface DepartmentService {

    DepartmentResponseDto createDepartment(DepartmentRequestDto dto);
    DepartmentResponseDto fetchDepartmentById(Long id);
    List<DepartmentResponseDto> fetchAllDepartments();
    DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto dto);
    void deleteDepartment(Long id);
    DepartmentResponseDto getDepartmentByEmployeeId(Long id);
}