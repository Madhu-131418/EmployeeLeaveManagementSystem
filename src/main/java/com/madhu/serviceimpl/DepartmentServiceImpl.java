package com.madhu.serviceimpl;

import com.madhu.requestdto.*;
import com.madhu.responsedto.*;
import com.madhu.entity.*;
import com.madhu.exception.ResourceNotFoundException;
import com.madhu.repository.*;
import com.madhu.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto dto) {
        Department dept = new Department();
        dept.setDepartmentCode(dto.getDepartmentCode());
        dept.setDepartmentName(dto.getDepartmentName());
        dept.setLocation(dto.getLocation());
        Department savedDept = departmentRepository.save(dept);
        return map(savedDept);
    }

    @Override
    public DepartmentResponseDto fetchDepartmentById(Long id) {
    	Department dept=departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department Not Found"));
        return map(dept);
    }

    @Override
    public List<DepartmentResponseDto> fetchAllDepartments() {
        return departmentRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto dto) {
        Department d = departmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Department Not Found"));

        if (dto.getDepartmentCode() !=null) {
            d.setDepartmentCode(dto.getDepartmentCode());
        }

        if (dto.getDepartmentName() != null) {
            d.setDepartmentName(dto.getDepartmentName());
        }

        if (dto.getLocation() != null) {
            d.setLocation(dto.getLocation());
        }

        return map(departmentRepository.save(d));
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        departmentRepository.delete(department);
    }
    @Override
    public DepartmentResponseDto getDepartmentByEmployeeId(Long empId) {
        Employee emp = employeeRepository.findById(empId) .orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
        Department dept = emp.getDepartment();
        if (dept == null) {
            throw new ResourceNotFoundException("Department not assigned to employee");
        }
        return map(dept);
        
    }

    private DepartmentResponseDto map(Department d) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setDepartmentId(d.getDepartmentId());
        dto.setDepartmentName(d.getDepartmentName());
        dto.setDepartmentCode(d.getDepartmentCode());
        dto.setLocation(d.getLocation());
        return dto;
    }
}