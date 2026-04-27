package com.madhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.madhu.requestdto.DepartmentRequestDto;
import com.madhu.responsedto.DepartmentResponseDto;
import com.madhu.service.DepartmentService;
@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
    private  DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> addDepartment(@RequestBody DepartmentRequestDto dto) {
        return new ResponseEntity<>(departmentService.createDepartment(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.fetchDepartmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.fetchAllDepartments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable Long id,
                                                                  @RequestBody DepartmentRequestDto dto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully");
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<DepartmentResponseDto> getEmployees(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentByEmployeeId(id));
    }
}