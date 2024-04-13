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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alexpages.ordermanager.api.domain.Coordinates;
import com.alexpages.ordermanager.api.domain.GetOrderAuditRequest;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.OrderInputData;
import com.alexpages.ordermanager.api.domain.OrderPatchInput;
import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.alexpages.ordermanager.api.domain.Status;
import com.alexpages.ordermanager.entity.OrderAuditEntity;
import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.error.OrderManagerException409;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.mapper.OrderManagerMapper;
import com.alexpages.ordermanager.repository.OrderAuditRepository;
import com.alexpages.ordermanager.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderAuditRepository orderAuditRepository;
	@Mock
	private GoogleMapsServiceImpl googleMapsServiceImpl;
	@Mock
	private OrderManagerMapper orderMapper;

	private EasyRandom easyRandom;

	@BeforeEach
	public void setUp() {
		Randomizer<BigDecimal> randomizer = new org.jeasy.random.randomizers.range.BigDecimalRangeRandomizer(
				Double.valueOf("1"), Double.valueOf("100"));
		easyRandom = new EasyRandom(new EasyRandomParameters().randomize(BigDecimal.class, randomizer));
	}

	@Test
	void testPlaceOrder_success() throws Exception 
    {
    	when(googleMapsServiceImpl.getDistanceFromDistanceMatrix(any())).thenReturn(1);
    	when(orderRepository.save(any(OrderEntity.class))).thenReturn(generateValidOrderEntity());
    	assertNotNull(orderServiceImpl.postOrder(generateValidOrderPostRequest()));
    }

	@Test
    void testPlaceOrder_error() throws Exception 
    {
    	when(googleMapsServiceImpl.getDistanceFromDistanceMatrix(any())).thenThrow(new RuntimeException("some error"));
    	assertThrows(OrderManagerException500.class, () -> orderServiceImpl.postOrder(generateValidOrderPostRequest()));
    }
	
	@Test
    void testPlaceOrder_error_regex() throws Exception 
    {
		OrderPostRequest request1 = new OrderPostRequest();
	    request1.setCoordinates(createCoordinates(
	    		Arrays.asList("22.319", "114.169"),
	    		Arrays.asList("-1212.2948341", "114.2329")));
	    
		OrderPostRequest request2 = new OrderPostRequest();
	    request2.setCoordinates(createCoordinates(
	    		Arrays.asList("22.319", "114.169"),
	    		Arrays.asList("22.319", "-11114.2329")));
	    
		OrderPostRequest request3 = new OrderPostRequest();
	    request3.setCoordinates(createCoordinates(
	    		Arrays.asList("-1122.319", "114.169"),
	    		Arrays.asList("22.319", "114.169")));
	    
		OrderPostRequest request4 = new OrderPostRequest();
	    request4.setCoordinates(createCoordinates(
	    		Arrays.asList("22.319", "-1114.169"),
	    		Arrays.asList("22.319", "114.169")));

      	assertThrows(OrderManagerException500.class, () -> orderServiceImpl.postOrder(request1));
      	assertThrows(OrderManagerException500.class, () -> orderServiceImpl.postOrder(request2));
      	assertThrows(OrderManagerException500.class, () -> orderServiceImpl.postOrder(request3));
      	assertThrows(OrderManagerException500.class, () -> orderServiceImpl.postOrder(request4));
      	
    }

	@Test
	void testPlaceOrder_error_null() {
		assertThrows(NullPointerException.class, () -> orderServiceImpl.postOrder(null));
	}
	
	@Test
	void testDeleteOrder_success() throws Exception {
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
	    doNothing().when(orderRepository).deleteById(any());
	    when(orderAuditRepository.save(any())).thenReturn(easyRandom.nextObject(OrderAuditEntity.class));
	    assertDoesNotThrow(() -> orderServiceImpl.deleteOrderById(1L));
	}
	@Test
	void testDeleteOrder_error() throws Exception {
	    assertThrows(OrderManagerException500.class, () -> orderServiceImpl.deleteOrderById(1L));
	}

	@Test
	void testGetOrderList_success() 
	{
	    List<OrderEntity> lOrderEntities = new ArrayList<>();
	    lOrderEntities.add(generateValidOrderEntity());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(orderRepository.filterByParams(any(), any(), any(), any(), any())).thenReturn(new PageImpl<>(lOrderEntities, pageable, lOrderEntities.size()));
	    assertNotNull(orderServiceImpl.getOrderList(generateValidOrderInputData()));
	}

	@Test
    void testGetOrderList_error()
    {
	    when(orderRepository.filterByParams(any(), any(), any(), any(), any())).thenThrow(new RuntimeException("some error"));
		assertThrows(OrderManagerException500.class, () -> orderServiceImpl.getOrderList(generateValidOrderInputData()));
    }
	
	@Test
	void testGetOrderAuditList_success() 
	{
	    List<OrderAuditEntity> lOrderAuditEntities = new ArrayList<>();
	    lOrderAuditEntities.add(generateValidOrderAuditEntity());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(orderAuditRepository.filterByParams(any(), any(), any(), any(), any()))
	    				.thenReturn(new PageImpl<>(lOrderAuditEntities, pageable, lOrderAuditEntities.size()));
	    assertNotNull(orderServiceImpl.getAuditList(generateValidGetOrderAuditRequest()));
	}
	
	@Test
    void testGetOrderAuditList_error()
    {
		assertThrows(OrderManagerException500.class, () -> orderServiceImpl.getAuditList(null));
    }
	
	@Test
	void testTakeOrder_success() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
		when(orderRepository.save(any())).thenReturn(easyRandom.nextObject(OrderEntity.class));
		assertNotNull(orderServiceImpl.takeOrder(1L, generateValidOrderPatchInput()));
	}
	
	@Test
	void testTakeOrder_error_ConcurrencyError() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
		when(orderRepository.save(any())).thenThrow(new OptimisticLockingFailureException("some error"));
		assertThrows(OrderManagerException409.class, () -> orderServiceImpl.takeOrder(1L, generateValidOrderPatchInput()));
	}
	
	@Test
	void testTakeOrder_error_notFound() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(OrderManagerException404.class, () -> orderServiceImpl.takeOrder(1L, generateValidOrderPatchInput()));
	}
	
	@Test
	void testTakeOrder_error_sameStatus() 
	{
		when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
		OrderPatchInput orderPatchInput = new OrderPatchInput();
		orderPatchInput.setStatus(Status.UNASSIGNED);
		assertThrows(OrderManagerException409.class, () -> orderServiceImpl.takeOrder(1L, orderPatchInput));
	}
	
	@Test
	void testTakeOrder_error_null() 
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
	
	
	// Private functions to aid testing
	
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
	
	private OrderPostRequest generateValidOrderPostRequest() {
	    OrderPostRequest request = new OrderPostRequest();
	    request.setCoordinates(createCoordinates(
	    		Arrays.asList("22.319", "114.169"),
	    		Arrays.asList("22.2948341", "114.2329")));
	    return request;
	}
	
	private Coordinates createCoordinates(List<String> origin, List<String> destination) {
	    Coordinates coordinates = new Coordinates();
	    coordinates.setOrigin(origin);
	    coordinates.setDestination(destination);
	    return coordinates;
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
	
	
	private GetOrderAuditRequest generateValidGetOrderAuditRequest() {
		GetOrderAuditRequest request = new GetOrderAuditRequest();
		request.setPaginationBody(null);
		request.setOrderInputAudit(null);
		return request;
	}
	
	private OrderAuditEntity generateValidOrderAuditEntity() {
		return OrderAuditEntity.builder()
				.action("CREATE")
				.actionDate(LocalDateTime.now())
				.id(1L)
				.orderId(1L)
				.build();
	}

}
