package com.madhu.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.madhu.requestdto.LeaveTypeRequestDto;
import com.madhu.responsedto.LeaveTypeResponseDto;
import com.madhu.service.LeaveTypeService;

@RestController
@RequestMapping("/leavetypes")
public class LeaveTypeController 
{
    @Autowired
    private LeaveTypeService leaveTypeService;

    @PostMapping
    public ResponseEntity<LeaveTypeResponseDto> addLeaveType(@RequestBody LeaveTypeRequestDto dto) 
    {
        return new ResponseEntity<>(leaveTypeService.addLeaveType(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LeaveTypeResponseDto>> getAllLeaveTypes() 
    {
        return ResponseEntity.ok(leaveTypeService.getAllLeaveTypes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveTypeResponseDto> updateLeaveType(@PathVariable Long id,
                                                                @RequestBody LeaveTypeRequestDto dto) 
    {
        return ResponseEntity.ok(leaveTypeService.updateLeaveType(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeaveType(@PathVariable Long id) 
    {
        return ResponseEntity.ok(leaveTypeService.deleteLeaveType(id));
    }
}