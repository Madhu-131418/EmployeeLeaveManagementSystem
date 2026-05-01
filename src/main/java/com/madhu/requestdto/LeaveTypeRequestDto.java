package com.madhu.requestdto;

import lombok.Data;

@Data
public class LeaveTypeRequestDto 
{
    private String leaveName;
    private int maxDaysPerYear;
    private Boolean carryForwardAllowed;
}