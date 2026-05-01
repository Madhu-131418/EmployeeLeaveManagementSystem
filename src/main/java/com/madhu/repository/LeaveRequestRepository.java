package com.madhu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.madhu.entity.Employee;
import com.madhu.entity.LeaveRequest;
import com.madhu.util.LeaveStatus;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>
{
    List<LeaveRequest> findByEmployee(Employee employee);

    List<LeaveRequest> findByLeaveStatus(LeaveStatus leaveStatus);

    List<LeaveRequest> findByFromDateGreaterThanEqualAndToDateLessThanEqual(LocalDate fromDate, LocalDate toDate);
}