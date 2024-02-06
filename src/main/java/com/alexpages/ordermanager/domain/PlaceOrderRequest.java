package com.alexpages.ordermanager.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest {

	@JsonProperty("origin")
	@Size(min = 2, max = 2, message = "Origin must contain exactly two coordinates")
	@NotNull(message = "Origin coordinates must be informed")
	private String[] origin;

	@JsonProperty("destination")
	@Size(min = 2, max = 2, message = "Destination must contain exactly two coordinates")
	@NotNull(message = "Destination coordinates must be informed")
	private String[] destination;
}
