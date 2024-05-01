package com.alexpages.ordermanager.service;

import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.external.model.google.GoogleOrderData;

public interface GoogleMapsService {

	public GoogleOrderData getGoogleOrderDataFromDistanceMatrix(OrderPostRequest orderPostRequest);
	
}
