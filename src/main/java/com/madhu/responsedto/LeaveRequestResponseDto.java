package com.madhu.responsedto;

import java.time.LocalDate;

import com.madhu.util.LeaveStatus;

import lombok.Data;

@Data
public class LeaveRequestResponseDto 
{
    private Long leaveRequestId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int numberOfDays;
    private String reason;
    private LocalDate appliedDate;
    private LeaveStatus leaveStatus;
    private String managerComments;

    private Long employeeId;
    private String employeeName;

    private Long leaveTypeId;
    private String leaveTypeName;
}