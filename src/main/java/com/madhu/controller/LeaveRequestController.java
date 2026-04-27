package com.madhu.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.madhu.requestdto.LeaveRequestRequestDto;
import com.madhu.responsedto.LeaveRequestResponseDto;
import com.madhu.service.LeaveRequestService;

@RestController
@RequestMapping("/leaverequests")
public class LeaveRequestController 
{
    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveRequestResponseDto> applyLeave(@RequestBody LeaveRequestRequestDto dto) 
    {
        return new ResponseEntity<>(leaveRequestService.applyLeave(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestResponseDto> getLeaveRequestById(@PathVariable Long id) 
    {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestById(id));
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestResponseDto>> getAllLeaveRequests() 
    {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequestResponseDto> approveLeave(@PathVariable Long id,
            @RequestParam String managerComments)
    {
        return ResponseEntity.ok(leaveRequestService.approveLeave(id, managerComments));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequestResponseDto> rejectLeave(@PathVariable Long id,
            @RequestParam String managerComments)
    {
        return ResponseEntity.ok(leaveRequestService.rejectLeave(id, managerComments));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<LeaveRequestResponseDto> cancelLeave(@PathVariable Long id)
    {
        return ResponseEntity.ok(leaveRequestService.cancelLeave(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequestResponseDto>> getLeavesByEmployee(@PathVariable Long employeeId) 
    {
        return ResponseEntity.ok(leaveRequestService.getLeavesByEmployee(employeeId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaveRequestResponseDto>> getLeavesByStatus(@PathVariable String status) 
    {
        return ResponseEntity.ok(leaveRequestService.getLeavesByStatus(status));
    }

    @GetMapping("/dates")
    public ResponseEntity<List<LeaveRequestResponseDto>> getLeavesBetweenDates(@RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) 
    {
        return ResponseEntity.ok(leaveRequestService.getLeavesBetweenDates(fromDate, toDate));
    }
}