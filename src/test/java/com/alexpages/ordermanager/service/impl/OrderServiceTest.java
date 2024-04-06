package com.alexpages.ordermanager.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alexpages.ordermanager.api.domain.Coordinates;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.OrderInputData;
import com.alexpages.ordermanager.api.domain.OrderPatchInput;
import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.alexpages.ordermanager.api.domain.Status;
import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.error.OrderManagerException400;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.error.OrderManagerException409;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.mapper.OrderMapper;
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
	void testDeleteOrderSuccess() throws Exception {
		when(orderRepository.existsById(any())).thenReturn(true);
	    doNothing().when(orderRepository).deleteById(any());
	    assertDoesNotThrow(() -> orderServiceImpl.deleteOrderById(1L));
	}
	@Test
	void testDeleteOrderError() throws Exception {
		when(orderRepository.existsById(any())).thenReturn(false);
	    assertThrows(OrderManagerException404.class, () -> orderServiceImpl.deleteOrderById(1L));
	}

	@Test
	void testGetOrderListSuccess() 
	{
	    List<OrderEntity> lOrderEntities = new ArrayList<>();
	    lOrderEntities.add(generateValidOrderEntity());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(orderRepository.filterByParams(any(), any(), any(), any(), any())).thenReturn(new PageImpl<>(lOrderEntities, pageable, lOrderEntities.size()));
	    assertNotNull(orderServiceImpl.getOrderList(generateValidOrderInputData()));
	}

	@Test
    void testGetOrderListError()
    {
	    when(orderRepository.filterByParams(any(), any(), any(), any(), any())).thenThrow(new RuntimeException("some error"));
		assertThrows(OrderManagerException500.class, () -> orderServiceImpl.getOrderList(generateValidOrderInputData()));
    }
	
	@Test
	void testTakeOrderSuccess() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
		when(orderRepository.save(any())).thenReturn(easyRandom.nextObject(OrderEntity.class));
		assertNotNull(orderServiceImpl.takeOrder(1L, generateValidOrderPatchInput()));
	}
	
	@Test
	void testTakeOrderError_ConcurrencyError() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
		when(orderRepository.save(any())).thenThrow(new OptimisticLockingFailureException("some error"));
		assertThrows(OrderManagerException409.class, () -> orderServiceImpl.takeOrder(1L, generateValidOrderPatchInput()));
	}
	
	@Test
	void testTakeOrderError_OrderNotFound() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(OrderManagerException404.class, () -> orderServiceImpl.takeOrder(1L, generateValidOrderPatchInput()));
	}
	
	@Test
	void testTakeOrderError_WrongStatus() 
	{
		assertThrows(OrderManagerException400.class, () -> orderServiceImpl.takeOrder(1L, generateWrongTakeOrderByIdRequest()));
	}
	
	@Test
	void testTakeOrderError_OrderTaken() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateTakenOrderEntity()));
		assertThrows(OrderManagerException409.class, () -> orderServiceImpl.takeOrder(1L, generateValidOrderPatchInput()));
	}
	
	@Test
	void testTakeOrderError_Null() 
	{
		assertThrows(NullPointerException.class, () -> orderServiceImpl.takeOrder(null, generateValidOrderPatchInput()));
		assertThrows(NullPointerException.class, () -> orderServiceImpl.takeOrder(1L, null));
	}
	
	@Test
	void testGetOrderDetails_success() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
		when(orderMapper.toOrderDetails(any(OrderEntity.class))).thenReturn(generateValidOrderDetails());
		assertNotNull(orderServiceImpl.getOrderDetail(1L));
	}
	
	private OrderDetails generateValidOrderDetails() {
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCreationDate(LocalDate.now());
		orderDetails.setDescription("Pickup for Carlos");
		orderDetails.setId(1L);
		orderDetails.setStatus(Status.UNASSIGNED);
		orderDetails.setVersion(1L);
		return orderDetails;
	}

	@Test
	void testGetOrderDetails_null() {
	    when(orderRepository.findById(any())).thenReturn(Optional.empty());
	    assertNull(orderServiceImpl.getOrderDetail(1L));
	}
	
	private OrderInputData generateValidOrderInputData() {
		OrderInputData orderInputData = new OrderInputData();
		orderInputData.setInputSearch(null);
		PaginationBody pagination = new PaginationBody();
		pagination.setPage(new BigDecimal(2));
		pagination.setSize(new BigDecimal(10));
		orderInputData.setPaginationBody(pagination);
		return orderInputData;
	}

	private OrderPatchInput generateValidOrderPatchInput() {
		OrderPatchInput input = new OrderPatchInput();
		input.setStatus(Status.TAKEN);
		return input;
	}
	
	private OrderPatchInput generateWrongTakeOrderByIdRequest() {
		OrderPatchInput input = new OrderPatchInput();
		input.setStatus(Status.SUCCESS);
		return input;
	}

	private OrderPostRequest generateWrongOrderPostRequest() {
	    OrderPostRequest request = new OrderPostRequest();
	    Coordinates coordinates = new Coordinates();
	    coordinates.setOrigin(Arrays.asList("22.319", "114.169"));
	    coordinates.setDestination(Arrays.asList("-1212.2948341", "114.2329"));
	    request.setCoordinates(coordinates);
	    return request;
	}
	
	private OrderPostRequest generateValidOrderPostRequest() {
	    OrderPostRequest request = new OrderPostRequest();
	    Coordinates coordinates = new Coordinates();
	    coordinates.setOrigin(Arrays.asList("22.319", "114.169"));
	    coordinates.setDestination(Arrays.asList("22.2948341", "114.2329"));

	    request.setCoordinates(coordinates);
	    return request;
	}

	private OrderEntity generateValidOrderEntity() {
		return OrderEntity.builder()
				.id(1L)
				.distance(100)
				.status(Status.UNASSIGNED.getValue())
				.description("Pickup for Carlos")
				.creationDate(LocalDateTime.now())
				.build();
	}
	
	private OrderEntity generateTakenOrderEntity() {
		return OrderEntity.builder().distance(1).status(Status.TAKEN.getValue()).build();
	}
}
