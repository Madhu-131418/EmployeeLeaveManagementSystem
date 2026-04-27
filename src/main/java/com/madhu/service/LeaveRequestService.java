package com.madhu.service;

import java.time.LocalDate;
import java.util.List;

import com.madhu.requestdto.LeaveRequestRequestDto;
import com.madhu.responsedto.LeaveRequestResponseDto;

public interface LeaveRequestService 
{
    LeaveRequestResponseDto applyLeave(LeaveRequestRequestDto dto);

    LeaveRequestResponseDto getLeaveRequestById(Long id);

    List<LeaveRequestResponseDto> getAllLeaveRequests();

    LeaveRequestResponseDto approveLeave(Long leaveRequestId, String managerComments);
    
    LeaveRequestResponseDto rejectLeave(Long leaveRequestId, String managerComments);
    
    LeaveRequestResponseDto cancelLeave(Long leaveRequestId);

    List<LeaveRequestResponseDto> getLeavesByEmployee(Long employeeId);

    List<LeaveRequestResponseDto> getLeavesByStatus(String status);

    List<LeaveRequestResponseDto> getLeavesBetweenDates(LocalDate fromDate, LocalDate toDate);
}