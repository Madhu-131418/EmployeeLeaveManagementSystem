package com.madhu.service;

import java.util.List;

import com.madhu.requestdto.LeaveTypeRequestDto;
import com.madhu.responsedto.LeaveTypeResponseDto;

public interface LeaveTypeService 
{
    LeaveTypeResponseDto addLeaveType(LeaveTypeRequestDto dto);

    List<LeaveTypeResponseDto> getAllLeaveTypes();

    LeaveTypeResponseDto updateLeaveType(Long id, LeaveTypeRequestDto dto);

    String deleteLeaveType(Long id);
}