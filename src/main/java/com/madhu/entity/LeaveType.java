package com.madhu.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="leave_type")
public class LeaveType 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leaveTypeId;
	private String leaveName;
	@OneToMany(mappedBy = "leaveType", cascade=CascadeType.ALL)
	private List<LeaveRequest> leaveRequests;
	@OneToMany(mappedBy = "leaveType", cascade=CascadeType.ALL)
	private List<LeaveBalance> leaveBalances;
	/**Example;
	Sick Leave
	Casual Leave, Emergency Leave, Unpaid Leave,
	Earned Leave, Compensatory Leave*/
	private int maxDaysPerYear;
	private Boolean carryForwardAllowed;
}
