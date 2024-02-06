package com.alexpages.ordermanager.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.alexpages.ordermanager.domain.PlaceOrderRequest;
import com.alexpages.ordermanager.error.OrderManagerException500;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GoogleMapsServiceTest {

    private GoogleMapsServiceImpl googleMapsServiceImpl;
    
	@Value("${thirdparties.google.key}")
	private String key;
    
    @BeforeEach
    void setUp() {
        googleMapsServiceImpl = new GoogleMapsServiceImpl();
    }

	@Test
    void testGetDistanceFromDistanceMatrixSuccess() 
	throws Exception 
	{
		if (!"YOUR_API_KEY".equals(key)) { //TODO Precondition to avoid failing test: Introduce key on application.yml
	        ReflectionTestUtils.setField(googleMapsServiceImpl, "key", key);
			assertNotNull(googleMapsServiceImpl.getDistanceFromDistanceMatrix(generateValidPlaceOrderRequest()));
		}
    }
	
	@Test
    void testGetDistanceFromDistanceMatrixError() 
	throws Exception 
	{
		if (!"YOUR_API_KEY".equals(key)) { //TODO Precondition to avoid failing test: Introduce key on application.yml
	        ReflectionTestUtils.setField(googleMapsServiceImpl, "key", key);
	        assertThrows(OrderManagerException500.class, () ->googleMapsServiceImpl.getDistanceFromDistanceMatrix(generateWrongPlaceOrderRequest()));
		}
    }
	
    private PlaceOrderRequest generateValidPlaceOrderRequest() {
		return PlaceOrderRequest.builder().origin(new String[] { "22.319", "114.169" })
				.destination(new String[] { "22.2948341", "114.2329" }).build();
	}
    
    private PlaceOrderRequest generateWrongPlaceOrderRequest() {
		return PlaceOrderRequest.builder().origin(new String[] { "22.319", "114.169" })
				.destination(new String[] { "-1122.2948341", "114.2329" }).build();
	}
}