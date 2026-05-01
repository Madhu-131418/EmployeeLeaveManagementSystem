package com.madhu.requestdto;

import lombok.Data;

@Data
public class LeaveBalanceRequestDto 
{
    private Long employeeId;
    private Long leaveTypeId;
    private Integer totalAllocated;
    private Integer usedLeaves;
    private Integer year;
}