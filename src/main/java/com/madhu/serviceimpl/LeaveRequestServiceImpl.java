package com.madhu.serviceimpl;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;

import com.madhu.entity.Employee;
import com.madhu.entity.LeaveBalance;
import com.madhu.entity.LeaveRequest;
import com.madhu.entity.LeaveType;
import com.madhu.exception.InsufficientLeaveBalanceException;
import com.madhu.exception.InvalidLeaveRequestException;
import com.madhu.exception.ResourceNotFoundException;
import com.madhu.repository.EmployeeRepository;
import com.madhu.repository.LeaveBalanceRepository;
import com.madhu.repository.LeaveRequestRepository;
import com.madhu.repository.LeaveTypeRepository;
import com.madhu.requestdto.LeaveRequestRequestDto;
import com.madhu.responsedto.LeaveRequestResponseDto;
import com.madhu.service.LeaveRequestService;
import com.madhu.util.LeaveStatus;

import jakarta.transaction.Transactional;
@Service
public class LeaveRequestServiceImpl implements LeaveRequestService
{
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
   
    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository,
			LeaveTypeRepository leaveTypeRepository, LeaveBalanceRepository leaveBalanceRepository) {
		super();
		this.leaveRequestRepository = leaveRequestRepository;
		this.employeeRepository = employeeRepository;
		this.leaveTypeRepository = leaveTypeRepository;
		this.leaveBalanceRepository = leaveBalanceRepository;
	}

	@Override
    public LeaveRequestResponseDto applyLeave(LeaveRequestRequestDto dto) 
    {
    	Employee employee = employeeRepository.findById(dto.getEmployeeId())
    	        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

    	LeaveType leaveType = leaveTypeRepository.findById(dto.getLeaveTypeId())
    	        .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));
    	
    	Integer currentYear = dto.getFromDate().getYear();
    	LeaveBalance balance = leaveBalanceRepository.findByEmployeeAndLeaveTypeAndYear(employee, leaveType, currentYear)
    	    .orElseThrow(() -> new ResourceNotFoundException("No leave balance found for this type and year"));

    	if (dto.getFromDate() == null || dto.getToDate() == null)
    	{
    	    throw new InvalidLeaveRequestException("From date and To date are required");
    	}

    	if (dto.getToDate().isBefore(dto.getFromDate()))
    	{
    	    throw new InvalidLeaveRequestException("To date cannot be before From date");
    	}

    	if (dto.getReason() == null || dto.getReason().trim().isEmpty())
    	{
    	    throw new InvalidLeaveRequestException("Reason is required");
    	}

        int days = (int) ChronoUnit.DAYS.between(dto.getFromDate(), dto.getToDate()) + 1;
    	if (balance.getRemainingLeaves() < days) 
    	    throw new InsufficientLeaveBalanceException("Insufficient balance. Requested: " + days+ ", Available: " + balance.getRemainingLeaves());

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setFromDate(dto.getFromDate());
        leaveRequest.setToDate(dto.getToDate());
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setAppliedDate(LocalDate.now());
        leaveRequest.setNumberOfDays(days);
        leaveRequest.setLeaveStatus(LeaveStatus.PENDING);
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(leaveType);

        return map(leaveRequestRepository.save(leaveRequest));
    	}
    

    @Override
    public LeaveRequestResponseDto getLeaveRequestById(Long id) 
    {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave Request not found"));

        return map(leaveRequest);
    }

    @Override
    public List<LeaveRequestResponseDto> getAllLeaveRequests() 
    {
        return leaveRequestRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }
    @Override
    @Transactional
    public LeaveRequestResponseDto approveLeave(Long leaveRequestId, String managerComments)
    {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING)
        {
            throw new InvalidLeaveRequestException("Only pending leave requests can be approved");
        }

        Employee employee = leaveRequest.getEmployee();
        LeaveType leaveType = leaveRequest.getLeaveType();
        Integer year = leaveRequest.getFromDate().getYear();

        LeaveBalance leaveBalance = leaveBalanceRepository
                .findByEmployeeAndLeaveTypeAndYear(employee, leaveType, year)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));

        if (leaveBalance.getRemainingLeaves() < leaveRequest.getNumberOfDays())
        {
            throw new InsufficientLeaveBalanceException("Insufficient leave balance");
        }

        leaveBalance.setUsedLeaves(leaveBalance.getUsedLeaves() + leaveRequest.getNumberOfDays());
        leaveBalance.setRemainingLeaves(leaveBalance.getTotalAllocated() - leaveBalance.getUsedLeaves());
        leaveBalanceRepository.save(leaveBalance);

        leaveRequest.setLeaveStatus(LeaveStatus.APPROVED);
        leaveRequest.setManagerComments(managerComments);

        return map(leaveRequestRepository.save(leaveRequest));
    }
    @Override
    public LeaveRequestResponseDto rejectLeave(Long leaveRequestId, String managerComments)
    {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING)
        {
            throw new InvalidLeaveRequestException("Only pending leave requests can be rejected");
        }

        leaveRequest.setLeaveStatus(LeaveStatus.REJECTED);
        leaveRequest.setManagerComments(managerComments);

        return map(leaveRequestRepository.save(leaveRequest));
    }
    @Override
    public LeaveRequestResponseDto cancelLeave(Long leaveRequestId)
    {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (leaveRequest.getLeaveStatus() == LeaveStatus.CANCELLED)
        {
            throw new InvalidLeaveRequestException("Leave request is already cancelled");
        }

        if (leaveRequest.getLeaveStatus() == LeaveStatus.REJECTED)
        {
            throw new InvalidLeaveRequestException("Rejected leave request cannot be cancelled");
        }

        if (leaveRequest.getLeaveStatus() == LeaveStatus.APPROVED)
        {
            Employee employee = leaveRequest.getEmployee();
            LeaveType leaveType = leaveRequest.getLeaveType();
            Integer year = leaveRequest.getFromDate().getYear();

            LeaveBalance leaveBalance = leaveBalanceRepository
                    .findByEmployeeAndLeaveTypeAndYear(employee, leaveType, year)
                    .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));

            leaveBalance.setUsedLeaves(leaveBalance.getUsedLeaves() - leaveRequest.getNumberOfDays());

            if (leaveBalance.getUsedLeaves() < 0)
            {
                leaveBalance.setUsedLeaves(0);
            }

            leaveBalance.setRemainingLeaves(leaveBalance.getTotalAllocated() - leaveBalance.getUsedLeaves());
            leaveBalanceRepository.save(leaveBalance);
        }

        leaveRequest.setLeaveStatus(LeaveStatus.CANCELLED);

        return map(leaveRequestRepository.save(leaveRequest));
    }
  
    @Override
    public List<LeaveRequestResponseDto> getLeavesByEmployee(Long employeeId) 
    {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee  not found"));

        return leaveRequestRepository.findByEmployee(employee)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<LeaveRequestResponseDto> getLeavesByStatus(String status) 
    {
    	LeaveStatus leaveStatus;
    	try {
    	    leaveStatus = LeaveStatus.valueOf(status.toUpperCase());
    	} catch (IllegalArgumentException ex) {
    	    throw new InvalidLeaveRequestException("Invalid leave status: " + status);
    	}

        return leaveRequestRepository.findByLeaveStatus(leaveStatus)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<LeaveRequestResponseDto> getLeavesBetweenDates(LocalDate fromDate, LocalDate toDate) 
    {
        return leaveRequestRepository.findByFromDateGreaterThanEqualAndToDateLessThanEqual(fromDate, toDate)
                .stream()
                .map(this::map)
                .toList();
    }

    private LeaveRequestResponseDto map(LeaveRequest leaveRequest)
    {
        LeaveRequestResponseDto dto = new LeaveRequestResponseDto();

        dto.setLeaveRequestId(leaveRequest.getLeaveRequestId());
        dto.setFromDate(leaveRequest.getFromDate());
        dto.setToDate(leaveRequest.getToDate());
        dto.setNumberOfDays(leaveRequest.getNumberOfDays());
        dto.setReason(leaveRequest.getReason());
        dto.setAppliedDate(leaveRequest.getAppliedDate());
        dto.setLeaveStatus(leaveRequest.getLeaveStatus());
        dto.setManagerComments(leaveRequest.getManagerComments());

        if (leaveRequest.getEmployee() != null)
        {
            dto.setEmployeeId(leaveRequest.getEmployee().getEmployeeId());
            dto.setEmployeeName(leaveRequest.getEmployee().getFirstName() + " " +
                    leaveRequest.getEmployee().getLastName());
        }

        if (leaveRequest.getLeaveType() != null)
        {
            dto.setLeaveTypeId(leaveRequest.getLeaveType().getLeaveTypeId());
            dto.setLeaveTypeName(leaveRequest.getLeaveType().getLeaveName());
        }

        return dto;
    }
}