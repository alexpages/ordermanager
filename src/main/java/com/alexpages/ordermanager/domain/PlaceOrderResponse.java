package com.alexpages.ordermanager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrderResponse {

	@JsonProperty("id")
    private Long id;
	
	@JsonProperty("distance")
    private long distance;
    
	@JsonProperty("status")
    private String status;
}