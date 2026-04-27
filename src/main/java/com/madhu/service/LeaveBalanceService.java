package com.madhu.service;

import java.util.List;
import com.madhu.requestdto.LeaveBalanceRequestDto;
import com.madhu.responsedto.LeaveBalanceResponseDto;

public interface LeaveBalanceService 
{
    LeaveBalanceResponseDto allocateLeaveBalance(LeaveBalanceRequestDto dto);

    LeaveBalanceResponseDto getLeaveBalanceById(Long leaveBalanceId);

    List<LeaveBalanceResponseDto> getAllBalancesByEmployee(Long employeeId);

    LeaveBalanceResponseDto updateLeaveBalance(Long leaveBalanceId, LeaveBalanceRequestDto dto);

    LeaveBalanceResponseDto getEmployeeLeaveBalanceByTypeAndYear(Long employeeId, Long leaveTypeId, Integer year);

}