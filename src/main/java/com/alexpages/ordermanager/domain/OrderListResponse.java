package com.alexpages.ordermanager.domain;

import java.util.List;

import com.alexpages.ordermanager.entity.OrderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderListResponse {
	
	@JsonProperty("orders")
	private List<OrderDTO> orders;
	
}
