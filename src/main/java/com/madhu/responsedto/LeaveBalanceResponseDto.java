package com.madhu.responsedto;


import lombok.Data;

@Data
public class LeaveBalanceResponseDto 
{
    private Long leaveBalanceId;
    private Long employeeId;
    private String employeeName;
    private Long leaveTypeId;
    private String leaveTypeName;
    private Integer totalAllocated;
    private Integer usedLeaves;
    private Integer remainingLeaves;
    private Integer year;
}