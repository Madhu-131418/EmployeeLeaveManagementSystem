package com.madhu.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madhu.entity.LeaveType;
import com.madhu.exception.ResourceNotFoundException;
import com.madhu.repository.LeaveTypeRepository;
import com.madhu.requestdto.LeaveTypeRequestDto;
import com.madhu.responsedto.LeaveTypeResponseDto;
import com.madhu.service.LeaveTypeService;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService
{
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveTypeResponseDto addLeaveType(LeaveTypeRequestDto dto) 
    {
        LeaveType leaveType = new LeaveType();
        leaveType.setLeaveName(dto.getLeaveName());
        leaveType.setMaxDaysPerYear(dto.getMaxDaysPerYear());
        leaveType.setCarryForwardAllowed(dto.getCarryForwardAllowed());
        return map(leaveTypeRepository.save(leaveType));
    }
    @Override
    public List<LeaveTypeResponseDto> getAllLeaveTypes() 
    {
        return leaveTypeRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }
    @Override
    public LeaveTypeResponseDto updateLeaveType(Long id, LeaveTypeRequestDto dto) 
    {
        LeaveType leaveType = leaveTypeRepository.findById(id) .orElseThrow(()-> new ResourceNotFoundException("Leavetype Not Found"));
        if (dto.getLeaveName() != null) 
        {
            leaveType.setLeaveName(dto.getLeaveName());
        }
        leaveType.setMaxDaysPerYear(dto.getMaxDaysPerYear());
        leaveType.setCarryForwardAllowed(dto.getCarryForwardAllowed());
        return map(leaveTypeRepository.save(leaveType));
    }
    @Override
    public String deleteLeaveType(Long id) 
    {
        LeaveType leaveType = leaveTypeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Leavetype Not Found"));
        leaveTypeRepository.delete(leaveType);
        return "Leave type deleted successfully";
    }

    private LeaveTypeResponseDto map(LeaveType leaveType) 
    {
        LeaveTypeResponseDto dto = new LeaveTypeResponseDto();
        dto.setLeaveTypeId(leaveType.getLeaveTypeId());
        dto.setLeaveName(leaveType.getLeaveName());
        dto.setMaxDaysPerYear(leaveType.getMaxDaysPerYear());
        dto.setCarryForwardAllowed(leaveType.getCarryForwardAllowed());
        return dto;
    }
}