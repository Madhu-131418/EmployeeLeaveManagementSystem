package com.madhu.controller;

import com.madhu.requestdto.EmployeeRequestDto;
import com.madhu.responsedto.EmployeeResponseDto;
import com.madhu.responsedto.LeaveRequestResponseDto;
import com.madhu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
    private  EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> addEmployee(@RequestBody EmployeeRequestDto dto) {
        return new ResponseEntity<>(employeeService.registerEmployee(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.fetchEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.fetchAllEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id,
                                                              @RequestBody EmployeeRequestDto dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.removeEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<List<EmployeeResponseDto>> getByDepartment(@PathVariable Long deptId) {
        return ResponseEntity.ok(employeeService.fetchEmployeesByDepartment(deptId));
    }

    @GetMapping("/{id}/leave-history")
    public ResponseEntity<List<LeaveRequestResponseDto>> getLeaveHistory(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeLeaveHistory(id));
    }
}