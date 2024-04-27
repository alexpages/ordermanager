package com.alexpages.ordermanager.external.model.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GoogleOrderData {
	
	private String originAddress;
	
	private String destinationAddress;
	
	private String time;
	
	private int distance;

}
