package com.madhu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "leave_balance")
public class LeaveBalance 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveBalanceId;

    private Integer totalAllocated;
    private Integer usedLeaves;
    private Integer remainingLeaves;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id")
    private LeaveType leaveType;
}