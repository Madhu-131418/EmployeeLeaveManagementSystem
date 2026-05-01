package com.madhu.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.madhu.entity.Employee;
import com.madhu.entity.LeaveBalance;
import com.madhu.entity.LeaveType;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long>
{
    List<LeaveBalance> findByEmployee(Employee employee);

    List<LeaveBalance> findByYear(Integer year);

    Optional<LeaveBalance> findByEmployeeAndLeaveTypeAndYear(Employee employee, LeaveType leaveType, Integer year);

    List<LeaveBalance> findByEmployeeEmployeeId(Long employeeId);
}