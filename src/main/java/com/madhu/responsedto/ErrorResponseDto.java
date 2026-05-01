package com.madhu.responsedto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto 
{
    private String message;
    private int status;
    private LocalDateTime timestamp;
}