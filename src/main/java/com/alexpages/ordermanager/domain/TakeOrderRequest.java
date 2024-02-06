package com.alexpages.ordermanager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TakeOrderRequest {
	
	@JsonProperty("status")
	private String status;
}