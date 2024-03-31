package com.alexpages.ordermanager.service;

import com.alexpages.ordermanager.domain.OrderPostRequest;
import com.alexpages.ordermanager.domain.PlaceOrderRequest;

import lombok.NonNull;

public interface GoogleMapsService 
{
	
	public int getDistanceFromDistanceMatrix(@NonNull OrderPostRequest orderPostRequest) throws Exception;
	
}
