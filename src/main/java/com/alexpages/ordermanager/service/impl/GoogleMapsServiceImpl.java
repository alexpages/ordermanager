package com.alexpages.ordermanager.service.impl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alexpages.ordermanager.domain.OrderPostRequest;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.service.GoogleMapsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixRow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleMapsServiceImpl implements GoogleMapsService 
{
	@Value("${thirdparties.google.key}")
	private String key;

	@Override
	public int getDistanceFromDistanceMatrix(@NonNull OrderPostRequest orderPostRequest) throws Exception 
	{
	    log.info("GoogleMapsServiceImpl > getDistanceFromDistanceMatrix > PlaceOrderRequest: {}", orderPostRequest);
    	DistanceMatrixRow[] rows = getDistanceMatrixRow(orderPostRequest);
        if (rows.length > 0 && rows[0].elements.length > 0 && rows[0].elements[0].distance != null) {
            long distanceInMeters = Math.toIntExact(rows[0].elements[0].distance.inMeters);
            log.info("Distance Matrix result: {} meters", distanceInMeters);
            return Math.toIntExact(distanceInMeters);
        } else {
            log.error("GoogleMapsServiceImpl > getDistanceFromDistanceMatrix > Invalid set of coordinates. Empty or invalid Distance Matrix result");
			throw new OrderManagerException500("GoogleMapsServiceImpl > getDistanceFromDistanceMatrix > Invalid set of coordinates. Empty or invalid Distance Matrix result");
        }
	}
	
	private DistanceMatrixRow[] getDistanceMatrixRow(@NonNull OrderPostRequest orderPostRequest) 
	throws ApiException, InterruptedException, IOException
	{
        GeoApiContext context = new GeoApiContext.Builder().apiKey(key).build();   
        
        DistanceMatrix distanceMatrix = DistanceMatrixApi.newRequest(context)
                .origins(String.join(",", orderPostRequest.getCoordinates().getOrigin()))
                .destinations(String.join(",", orderPostRequest.getCoordinates().getDestination()))
                .await();
        
        log.info("GoogleMapsServiceImpl > getDistanceMatrixRow > DistanceMatrix: " + printObject(distanceMatrix));       
        DistanceMatrixRow[] rows = distanceMatrix.rows;
        log.info("GoogleMapsServiceImpl > getDistanceMatrixRow > DistanceMatrixRow: " + printObject(rows));
        return rows;	
	}

	private String printObject(Object object) {
		ObjectMapper jsonMapper = new ObjectMapper();
		String json = null;
		try {
			json = jsonMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			json = e.getMessage();
		}
		return json;
	}
}