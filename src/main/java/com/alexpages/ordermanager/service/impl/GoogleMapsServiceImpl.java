package com.alexpages.ordermanager.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.external.model.google.GoogleOrderData;
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
//	@Value("${thirdparties.google.key}")
	private String key = "AIzaSyDFW_s8pzd3mG1OKTsZkLeKec0aYh5zVEw";
	
	@Override
	public GoogleOrderData getGoogleOrderDataFromDistanceMatrix(@NonNull OrderPostRequest orderPostRequest) 
	{
		final String LOG_PREFIX = "GoogleMapsServiceImpl > getGoogleOrderDataFromDistanceMatrix > ";   	
		try {
			
			DistanceMatrix distanceMatrix = getDistanceMatrix(orderPostRequest);
			
			return GoogleOrderData.builder()
					.distance(getDistanceFromDistanceMatrix(distanceMatrix))
					.destinationAddress(distanceMatrix.originAddresses[0])
					.originAddress(distanceMatrix.destinationAddresses[0])
					.time(getTimeFromDistanceMatrix(distanceMatrix))
					.build();		
			
		} catch (Exception e) {
			throw new OrderManagerException500(LOG_PREFIX + "There was an error with GoogleMaps API: {}", e);
		}	
	}
	
	private DistanceMatrix getDistanceMatrix(OrderPostRequest orderPostRequest) 
	throws ApiException, InterruptedException, IOException
	{
		final String LOG_PREFIX = "GoogleMapsServiceImpl > getDistanceMatrixRow > ";
        try {
    		GeoApiContext context = new GeoApiContext.Builder().apiKey(key).build();   
            DistanceMatrix distanceMatrix = DistanceMatrixApi.newRequest(context)
                    .origins(String.join(",", orderPostRequest.getCoordinates().getOrigin()))
                    .destinations(String.join(",", orderPostRequest.getCoordinates().getDestination()))
                    .await();
            
            log.info(LOG_PREFIX + "DistanceMatrix: " + printObject(distanceMatrix));    
            return distanceMatrix;
        	
        } catch (Exception e) {
        	log.error(LOG_PREFIX + "DistanceMatrix could not get retrieved: {}", e);
        	throw e; 
        }
	}
	
	private boolean validateDistanceMatrixRow(DistanceMatrixRow[] rows) 
	{
		return (rows.length > 0 && rows[0].elements.length > 0 && rows[0].elements[0].distance != null && rows[0].elements[0].duration != null);
	}
	
	private String getTimeFromDistanceMatrix(DistanceMatrix distanceMatrix) 
	throws Exception 
	{
		final String LOG_PREFIX = "GoogleMapsServiceImpl > getTimeFromDistanceMatrix > ";   	
		DistanceMatrixRow[] rows = distanceMatrix.rows;
		
		if (validateDistanceMatrixRow(rows)) {
            String timeHumanReadable = rows[0].elements[0].duration.humanReadable;
            log.info(LOG_PREFIX + "Time in human readable format: {}", timeHumanReadable);
            return timeHumanReadable;
        
        } else {
            log.error(LOG_PREFIX + "Invalid set of coordinates. Empty or invalid Distance Matrix result");
			throw new OrderManagerException500(LOG_PREFIX + "Invalid set of coordinates. Empty or invalid Distance Matrix result");
        }
	}

	private int getDistanceFromDistanceMatrix(DistanceMatrix distanceMatrix) 
	throws Exception 
	{
		final String LOG_PREFIX = "GoogleMapsServiceImpl > getDistanceFromDistanceMatrix > ";   	
		DistanceMatrixRow[] rows = distanceMatrix.rows;
		
		if (validateDistanceMatrixRow(rows)) {
            long distanceInMeters = Math.toIntExact(rows[0].elements[0].distance.inMeters);
            log.info(LOG_PREFIX + "Distance Matrix result: {} meters", distanceInMeters);
            return Math.toIntExact(distanceInMeters);
        
        } else {
            log.error(LOG_PREFIX + "Invalid set of coordinates. Empty or invalid Distance Matrix result");
			throw new OrderManagerException500(LOG_PREFIX + "Invalid set of coordinates. Empty or invalid Distance Matrix result");
        }
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