package com.madhu.serviceimpl;

import com.madhu.requestdto.*;
import com.madhu.responsedto.*;
import com.madhu.entity.*;
import com.madhu.exception.ResourceNotFoundException;
import com.madhu.repository.*;
import com.madhu.service.EmployeeService;
import com.madhu.util.EmploymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    

    @Override
    public EmployeeResponseDto registerEmployee(EmployeeRequestDto dto) {

        Department dept = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(()-> new ResourceNotFoundException("Department Not Found"));

        Employee emp = new Employee();
        emp.setEmployeeCode(dto.getEmployeeCode());
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setEmail(dto.getEmail());
        emp.setPhoneNumber(dto.getPhoneNumber());
        emp.setDesignation(dto.getDesignation());
        emp.setJoiningDate(dto.getJoiningDate());
        emp.setEmploymentStatus(dto.getEmploymentStatus());
        emp.setDepartment(dept);

        return mapToDto(employeeRepository.save(emp));
    }

    @Override
    public EmployeeResponseDto fetchEmployeeById(Long id) {
        return mapToDto(employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found")));
    }

    @Override
    public List<EmployeeResponseDto> fetchAllEmployees() {
        return employeeRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));

        if (dto.getFirstName() != null) {
            emp.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            emp.setLastName(dto.getLastName());
        }

        if (dto.getEmail() != null) {
            emp.setEmail(dto.getEmail());
        }

        if (dto.getPhoneNumber() != null) {
            emp.setPhoneNumber(dto.getPhoneNumber());
        }

        if (dto.getDesignation() != null) {
            emp.setDesignation(dto.getDesignation());
        }

        if (dto.getEmploymentStatus() != null) {
        	emp.setEmploymentStatus(
        		    EmploymentStatus.valueOf(dto.getEmploymentStatus().name())
        		);
        }
        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(dto.getDepartmentId())
            		.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            emp.setDepartment(dept);
        }
        return mapToDto(employeeRepository.save(emp));
    }

    public void removeEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employee.setEmploymentStatus(EmploymentStatus.INACTIVE);
        employeeRepository.save(employee);
    }
    

    @Override
    public List<EmployeeResponseDto> fetchEmployeesByDepartment(Long deptId) {
        Department dept = departmentRepository.findById(deptId).orElseThrow(()-> new ResourceNotFoundException("Department Not Found"));
        return employeeRepository.findByDepartment(dept).stream().map(this::mapToDto).toList();
    }

    @Override
    public List<LeaveRequestResponseDto> getEmployeeLeaveHistory(Long id) {
        Employee emp = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
        return leaveRequestRepository.findByEmployee(emp)
                .stream()
                .map(this::mapLeaveRequest)
                .toList();
    }

    private EmployeeResponseDto mapToDto(Employee e) {
        EmployeeResponseDto dto = new EmployeeResponseDto();

        dto.setEmployeeId(e.getEmployeeId());
        dto.setEmployeeCode(e.getEmployeeCode());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setPhoneNumber(e.getPhoneNumber());
        dto.setDesignation(e.getDesignation());
        dto.setJoiningDate(e.getJoiningDate());
        if (e.getDepartment() != null) {
            dto.setDepartmentId(e.getDepartment().getDepartmentId());
            dto.setDepartmentName(e.getDepartment().getDepartmentName());
        }
        dto.setEmploymentStatus(e.getEmploymentStatus() != null ? e.getEmploymentStatus().name() : null);

        return dto;
    }
    private LeaveRequestResponseDto mapLeaveRequest(LeaveRequest leaveRequest)
    {
        LeaveRequestResponseDto dto = new LeaveRequestResponseDto();
        dto.setLeaveRequestId(leaveRequest.getLeaveRequestId());
        dto.setFromDate(leaveRequest.getFromDate());
        dto.setToDate(leaveRequest.getToDate());
        dto.setNumberOfDays(leaveRequest.getNumberOfDays());
        dto.setReason(leaveRequest.getReason());
        dto.setAppliedDate(leaveRequest.getAppliedDate());
        dto.setLeaveStatus(leaveRequest.getLeaveStatus());
        dto.setManagerComments(leaveRequest.getManagerComments());

        if (leaveRequest.getEmployee() != null)
        {
            dto.setEmployeeId(leaveRequest.getEmployee().getEmployeeId());
            dto.setEmployeeName(leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName());
        }

        if (leaveRequest.getLeaveType() != null)
        {
            dto.setLeaveTypeId(leaveRequest.getLeaveType().getLeaveTypeId());
            dto.setLeaveTypeName(leaveRequest.getLeaveType().getLeaveName());
        }

        return dto;
    }
}