package com.alexpages.ordermanager.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {		
	
	private Long id;
    private int distance;
    private String status;
    
}
    
