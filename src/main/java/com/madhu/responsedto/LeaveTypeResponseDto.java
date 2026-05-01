package com.madhu.responsedto;

import lombok.Data;

@Data
public class LeaveTypeResponseDto 
{
    private Long leaveTypeId;
    private String leaveName;
    private int maxDaysPerYear;
    private Boolean carryForwardAllowed;
}