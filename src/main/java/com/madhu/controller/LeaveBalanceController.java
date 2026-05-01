package com.madhu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.madhu.requestdto.LeaveBalanceRequestDto;
import com.madhu.responsedto.LeaveBalanceResponseDto;
import com.madhu.service.LeaveBalanceService;

@RestController
@RequestMapping("/leave-balances")
public class LeaveBalanceController 
{
    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService) 
    {
        this.leaveBalanceService = leaveBalanceService;
    }

    @PostMapping
    public ResponseEntity<LeaveBalanceResponseDto> allocateLeaveBalance(@RequestBody LeaveBalanceRequestDto dto) 
    {
        return new ResponseEntity<>(leaveBalanceService.allocateLeaveBalance(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveBalanceResponseDto> getLeaveBalanceById(@PathVariable Long id) 
    {
        return ResponseEntity.ok(leaveBalanceService.getLeaveBalanceById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveBalanceResponseDto>> getAllBalancesByEmployee(@PathVariable Long employeeId) 
    {
        return ResponseEntity.ok(leaveBalanceService.getAllBalancesByEmployee(employeeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveBalanceResponseDto> updateLeaveBalance(@PathVariable Long id,
                                                                      @RequestBody LeaveBalanceRequestDto dto) 
    {
        return ResponseEntity.ok(leaveBalanceService.updateLeaveBalance(id, dto));
    }

    @GetMapping("/search")
    public ResponseEntity<LeaveBalanceResponseDto> getEmployeeLeaveBalanceByTypeAndYear(
            @RequestParam Long employeeId,
            @RequestParam Long leaveTypeId,
            @RequestParam Integer year) 
    {
        return ResponseEntity.ok(
                leaveBalanceService.getEmployeeLeaveBalanceByTypeAndYear(employeeId, leaveTypeId, year)
        );
    }
}