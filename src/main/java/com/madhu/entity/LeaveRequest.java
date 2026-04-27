package com.madhu.entity;

import java.time.LocalDate;
import com.madhu.util.LeaveStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="leave_request")
public class LeaveRequest 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leaveRequestId;
	private LocalDate fromDate;
	private LocalDate toDate;
	private int numberOfDays;
	private String reason;
	private LocalDate appliedDate;
	@Enumerated(EnumType.STRING)
	private LeaveStatus leaveStatus;
	/**Example:
	PENDING
	APPROVED
	REJECTED
	CANCELLED*/
	private String managerComments;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="emp_ID")
	private Employee employee;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="leave_type_ID")
	private LeaveType leaveType;
}
