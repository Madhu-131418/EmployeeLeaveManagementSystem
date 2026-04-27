package com.madhu.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.madhu.entity.Employee;
import com.madhu.entity.LeaveBalance;
import com.madhu.entity.LeaveType;
import com.madhu.exception.DuplicateResourceException;
import com.madhu.exception.InvalidLeaveRequestException;
import com.madhu.exception.ResourceNotFoundException;
import com.madhu.repository.EmployeeRepository;
import com.madhu.repository.LeaveBalanceRepository;
import com.madhu.repository.LeaveTypeRepository;
import com.madhu.requestdto.LeaveBalanceRequestDto;
import com.madhu.responsedto.LeaveBalanceResponseDto;
import com.madhu.service.LeaveBalanceService;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService
{
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveBalanceServiceImpl(LeaveBalanceRepository leaveBalanceRepository,
                                   EmployeeRepository employeeRepository,
                                   LeaveTypeRepository leaveTypeRepository)
    {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.employeeRepository = employeeRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override
    public LeaveBalanceResponseDto allocateLeaveBalance(LeaveBalanceRequestDto dto)
    {
        if (dto.getEmployeeId() == null)
        {
            throw new InvalidLeaveRequestException("Employee id is required");
        }

        if (dto.getLeaveTypeId() == null)
        {
            throw new InvalidLeaveRequestException("Leave type id is required");
        }

        if (dto.getYear() == null)
        {
            throw new InvalidLeaveRequestException("Year is required");
        }

        if (dto.getTotalAllocated() == null || dto.getTotalAllocated() < 0)
        {
            throw new InvalidLeaveRequestException("Total allocated leaves must be valid");
        }

        int usedLeaves = dto.getUsedLeaves() == null ? 0 : dto.getUsedLeaves();

        if (usedLeaves < 0)
        {
            throw new InvalidLeaveRequestException("Used leaves cannot be negative");
        }

        if (usedLeaves > dto.getTotalAllocated())
        {
            throw new InvalidLeaveRequestException("Used leaves cannot be greater than total allocated");
        }

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LeaveType leaveType = leaveTypeRepository.findById(dto.getLeaveTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));

        leaveBalanceRepository.findByEmployeeAndLeaveTypeAndYear(employee, leaveType, dto.getYear())
                .ifPresent(lb -> {
                    throw new DuplicateResourceException(
                            "Leave balance already exists for this employee, leave type and year");
                });

        LeaveBalance leaveBalance = new LeaveBalance();
        BeanUtils.copyProperties(dto, leaveBalance);

        leaveBalance.setEmployee(employee);
        leaveBalance.setLeaveType(leaveType);
        leaveBalance.setUsedLeaves(usedLeaves);
        leaveBalance.setRemainingLeaves(dto.getTotalAllocated() - usedLeaves);

        LeaveBalance savedLeaveBalance = leaveBalanceRepository.save(leaveBalance);

        return map(savedLeaveBalance);
    }

    @Override
    public LeaveBalanceResponseDto getLeaveBalanceById(Long leaveBalanceId)
    {
        LeaveBalance leaveBalance = leaveBalanceRepository.findById(leaveBalanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));

        return map(leaveBalance);
    }

    @Override
    public List<LeaveBalanceResponseDto> getAllBalancesByEmployee(Long employeeId)
    {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findByEmployee(employee);

        return leaveBalances.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveBalanceResponseDto updateLeaveBalance(Long leaveBalanceId, LeaveBalanceRequestDto dto)
    {
        LeaveBalance leaveBalance = leaveBalanceRepository.findById(leaveBalanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));

        if (dto.getTotalAllocated() == null || dto.getTotalAllocated() < 0)
        {
            throw new InvalidLeaveRequestException("Total allocated leaves must be valid");
        }

        int usedLeaves = dto.getUsedLeaves() == null ? leaveBalance.getUsedLeaves() : dto.getUsedLeaves();

        if (usedLeaves < 0)
        {
            throw new InvalidLeaveRequestException("Used leaves cannot be negative");
        }

        if (usedLeaves > dto.getTotalAllocated())
        {
            throw new InvalidLeaveRequestException("Used leaves cannot be greater than total allocated");
        }

        leaveBalance.setTotalAllocated(dto.getTotalAllocated());
        leaveBalance.setUsedLeaves(usedLeaves);
        leaveBalance.setRemainingLeaves(dto.getTotalAllocated() - usedLeaves);

        if (dto.getYear() != null)
        {
            leaveBalance.setYear(dto.getYear());
        }

        LeaveBalance updatedLeaveBalance = leaveBalanceRepository.save(leaveBalance);

        return map(updatedLeaveBalance);
    }

    @Override
    public LeaveBalanceResponseDto getEmployeeLeaveBalanceByTypeAndYear(Long employeeId, Long leaveTypeId, Integer year)
    {
        if (year == null)
        {
            throw new InvalidLeaveRequestException("Year is required");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));

        LeaveBalance leaveBalance = leaveBalanceRepository
                .findByEmployeeAndLeaveTypeAndYear(employee, leaveType, year)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));

        return map(leaveBalance);
    }

    private LeaveBalanceResponseDto map(LeaveBalance leaveBalance)
    {
        LeaveBalanceResponseDto dto = new LeaveBalanceResponseDto();
        BeanUtils.copyProperties(leaveBalance, dto);

        dto.setEmployeeId(leaveBalance.getEmployee().getEmployeeId());

        String fullName = leaveBalance.getEmployee().getFirstName() + " "
                + leaveBalance.getEmployee().getLastName();
        dto.setEmployeeName(fullName);

        dto.setLeaveTypeId(leaveBalance.getLeaveType().getLeaveTypeId());
        dto.setLeaveTypeName(leaveBalance.getLeaveType().getLeaveName());

        return dto;
    }
}