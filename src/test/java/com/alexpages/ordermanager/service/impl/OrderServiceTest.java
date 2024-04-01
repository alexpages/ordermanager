package com.alexpages.ordermanager.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.alexpages.ordermanager.domain.Coordinates;
import com.alexpages.ordermanager.domain.OrderDTO;
import com.alexpages.ordermanager.domain.OrderPostRequest;
import com.alexpages.ordermanager.domain.PlaceOrderRequest;
import com.alexpages.ordermanager.domain.TakeOrderRequest;
import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.enums.OrderStatusEnum;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.mapper.OrderMapper;
import com.alexpages.ordermanager.error.OrderManagerException400;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.error.OrderManagerException409;
import com.alexpages.ordermanager.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	@Mock
	private OrderRepository orderRepository;
	@Mock
	private GoogleMapsServiceImpl googleMapsServiceImpl;
	@Mock
	private OrderMapper orderMapper;

	private EasyRandom easyRandom;

	@BeforeEach
	public void setUp() {
		Randomizer<BigDecimal> randomizer = new org.jeasy.random.randomizers.range.BigDecimalRangeRandomizer(
				Double.valueOf("1"), Double.valueOf("100"));
		easyRandom = new EasyRandom(new EasyRandomParameters().randomize(BigDecimal.class, randomizer));
	}

	@Test
	void testPlaceOrderSuccess() throws Exception 
    {
    	when(googleMapsServiceImpl.getDistanceFromDistanceMatrix(any())).thenReturn(1);
    	when(orderRepository.save(any(OrderEntity.class))).thenReturn(generateValidOrderEntity());
    	assertNotNull(orderServiceImpl.postOrder(generateValidOrderPostRequest()));
    }

	@Test
    void testPlaceOrderError() throws Exception 
    {
    	when(googleMapsServiceImpl.getDistanceFromDistanceMatrix(any())).thenThrow(new RuntimeException("some error"));
    	assertThrows(OrderManagerException500.class, () -> orderServiceImpl.postOrder(generateValidOrderPostRequest()));
    }
	
	@Test
    void testPlaceOrderError_Regex() throws Exception 
    {
      	assertThrows(OrderManagerException500.class, () -> orderServiceImpl.postOrder(generateWrongOrderPostRequest()));
    }

	@Test
	void testPlaceOrderError_Null() {
		assertThrows(NullPointerException.class, () -> orderServiceImpl.postOrder(null));
	}

	@Test
	void testGetOrderListSuccess() 
	{
	    List<OrderEntity> orderEntities = easyRandom.objects(OrderEntity.class, 2).collect(Collectors.toList());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(orderRepository.findAll(pageable)).thenReturn(new PageImpl<>(orderEntities, pageable, orderEntities.size()));
	    when(orderMapper.toOrderDTOList(any())).thenReturn(easyRandom.objects(OrderDTO.class, 2).collect(Collectors.toList()));
	    assertNotNull(orderServiceImpl.getOrderList(2, 10));
	}

	@Test
    void testGetOrderListError()
    {
	   	when(orderRepository.findAll()).thenThrow(new RuntimeException("some error"));
		assertThrows(OrderManagerException500.class, () -> orderServiceImpl.getOrderList(1, 10));
    }
	@Test
    void testGetOrderListError_Null()
    {
		assertThrows(NullPointerException.class, () -> orderServiceImpl.getOrderList(null, 1));
		assertThrows(NullPointerException.class, () -> orderServiceImpl.getOrderList(1, null));
    }
	
	@Test
	void testTakeOrderSuccess() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
		when(orderRepository.save(any())).thenReturn(easyRandom.nextObject(OrderEntity.class));
		assertNotNull(orderServiceImpl.takeOrder(1L, generateValidTakeOrderRequest()));
	}
	
	@Test
	void testTakeOrderError_ConcurrencyError() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
		when(orderRepository.save(any())).thenThrow(new OptimisticLockingFailureException("some error"));
		assertThrows(OrderManagerException409.class, () -> orderServiceImpl.takeOrder(1L, generateValidTakeOrderRequest()));
	}
	
	@Test
	void testTakeOrderError_OrderNotFound() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(OrderManagerException404.class, () -> orderServiceImpl.takeOrder(1L, generateValidTakeOrderRequest()));
	}
	
	@Test
	void testTakeOrderError_WrongStatus() 
	{
		assertThrows(OrderManagerException400.class, () -> orderServiceImpl.takeOrder(1L, generateWrongTakeOrderRequest()));
	}
	
	@Test
	void testTakeOrderError_OrderTaken() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateTakenOrderEntity()));
		assertThrows(OrderManagerException409.class, () -> orderServiceImpl.takeOrder(1L, generateValidTakeOrderRequest()));
	}
	
	@Test
	void testTakeOrderError_Null() 
	{
		assertThrows(NullPointerException.class, () -> orderServiceImpl.takeOrder(null, generateValidTakeOrderRequest()));
		assertThrows(NullPointerException.class, () -> orderServiceImpl.takeOrder(1L, null));
	}


	private TakeOrderRequest generateValidTakeOrderRequest() {
		TakeOrderRequest takeOrderRequest = new TakeOrderRequest();
		takeOrderRequest.setStatus(OrderStatusEnum.TAKEN.getValue());
		return takeOrderRequest;
	}
	
	private TakeOrderRequest generateWrongTakeOrderRequest() {
		TakeOrderRequest takeOrderRequest = new TakeOrderRequest();
		takeOrderRequest.setStatus("wrongStatus");
		return takeOrderRequest;
	}

	
	private OrderPostRequest generateWrongOrderPostRequest() {
	    OrderPostRequest request = new OrderPostRequest();
	    Coordinates coordinates = new Coordinates();
	    coordinates.setOrigin(Arrays.asList("22.319", "114.169"));
	    coordinates.setDestination(Arrays.asList("22.2948341", "114.2329"));
	    request.setCoordinates(coordinates);
	    return request;
	}
	
	private OrderPostRequest generateValidOrderPostRequest() {
	    OrderPostRequest request = new OrderPostRequest();
	    Coordinates coordinates = new Coordinates();
	    coordinates.setOrigin(Arrays.asList("22.319", "114.169"));
	    coordinates.setDestination(Arrays.asList("-1122.2948341", "114.2329"));
	    request.setCoordinates(coordinates);
	    return request;
	}

	private OrderEntity generateValidOrderEntity() {
		return OrderEntity.builder().distance(1).status(OrderStatusEnum.UNASSIGNED.getValue()).build();
	}
	
	private OrderEntity generateTakenOrderEntity() {
		return OrderEntity.builder().distance(1).status(OrderStatusEnum.TAKEN.getValue()).build();
	}
}
