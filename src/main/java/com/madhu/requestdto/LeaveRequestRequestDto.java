package com.madhu.requestdto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LeaveRequestRequestDto 
{
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    //private String managerComments;

    private Long employeeId;
    private Long leaveTypeId;
}