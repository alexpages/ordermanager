package com.alexpages.ordermanager.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderManagerException {

	private HttpStatus status;
	
	private Throwable throwable;
	
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;
    
	private final String error; 
    
}
