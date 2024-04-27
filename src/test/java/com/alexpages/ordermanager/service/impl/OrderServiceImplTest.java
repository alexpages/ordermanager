package com.alexpages.ordermanager.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alexpages.ordermanager.api.domain.Action;
import com.alexpages.ordermanager.api.domain.Coordinates;
import com.alexpages.ordermanager.api.domain.GetOrderAuditRequest;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.OrderInputAudit;
import com.alexpages.ordermanager.api.domain.OrderInputData;
import com.alexpages.ordermanager.api.domain.OrderInputDataInputSearch;
import com.alexpages.ordermanager.api.domain.OrderPatchInput;
import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.alexpages.ordermanager.api.domain.Status;
import com.alexpages.ordermanager.entity.OrderAuditEntity;
import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.error.OrderManagerException409;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.external.model.google.GoogleOrderData;
import com.alexpages.ordermanager.mapper.OrderManagerMapper;
import com.alexpages.ordermanager.repository.OrderAuditRepository;
import com.alexpages.ordermanager.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

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
	@Mock
    private AuthenticationManager authenticationManager; 

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
    	when(googleMapsServiceImpl.getGoogleOrderDataFromDistanceMatrix(any())).thenReturn(generateValidGoogleOrderData());
    	when(orderRepository.save(any(OrderEntity.class))).thenReturn(generateValidOrderEntity());
    	mockSecurityContext();
    	assertNotNull(orderServiceImpl.postOrder(generateValidOrderPostRequest()));
    }
	
	@Test
    void testPlaceOrder_error() throws Exception 
    {
    	when(googleMapsServiceImpl.getGoogleOrderDataFromDistanceMatrix(any())).thenThrow(new RuntimeException("some error"));
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
	    mockSecurityContext();
	    assertDoesNotThrow(() -> orderServiceImpl.deleteOrderById(1L));
	}
	
	@Test
	void testGetOrders_nullparams_success() 
	{
	    List<OrderEntity> lOrderEntities = new ArrayList<>();
	    lOrderEntities.add(generateValidOrderEntity());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(orderRepository.filterByParams(any(), any(), any(), any(), any()))
	            .thenReturn(new PageImpl<>(lOrderEntities, pageable, lOrderEntities.size()));
	    assertNotNull(orderServiceImpl.getOrders(new OrderInputData()));
	}
	
	@Test
	void testGetOrders_success() {
	    List<OrderEntity> lOrderEntities = new ArrayList<>();
	    lOrderEntities.add(generateValidOrderEntity());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(orderRepository.filterByParams(any(), any(), any(), any(), any()))
	            .thenReturn(new PageImpl<>(lOrderEntities, pageable, lOrderEntities.size()));
	    assertNotNull(orderServiceImpl.getOrders(generateValidOrderInputData()));
	}
	
	@Test
	void testGetOrders_success_empty() 
	{
	    Pageable pageable = PageRequest.of(1, 10);
	    when(orderRepository.filterByParams(any(), any(), any(), any(), any())).thenReturn(new PageImpl<>(new ArrayList<>(), pageable, 0));
	    assertNull(orderServiceImpl.getOrders(generateValidOrderInputData()));
	}

	@Test
    void testGetOrders_error()
    {
	    when(orderRepository.filterByParams(any(), any(), any(), any(), any())).thenThrow(new RuntimeException("some error"));
		assertThrows(OrderManagerException500.class, () -> orderServiceImpl.getOrders(generateValidOrderInputData()));
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
	void testGetOrderAuditList_nullparams_success() 
	{
	    List<OrderAuditEntity> lOrderAuditEntities = new ArrayList<>();
	    lOrderAuditEntities.add(generateValidOrderAuditEntity());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(orderAuditRepository.filterByParams(any(), any(), any(), any(), any()))
	    				.thenReturn(new PageImpl<>(lOrderAuditEntities, pageable, lOrderAuditEntities.size()));
	    assertNotNull(orderServiceImpl.getAuditList(new GetOrderAuditRequest()));
	}
	
	
	@Test
    void testGetOrderAuditList_error()
    {
		assertThrows(OrderManagerException500.class, () -> orderServiceImpl.getAuditList(null));
    }
	
	@Test
	void testGetOrderAuditList_success_empty() 
	{
	    assertNull(orderServiceImpl.getAuditList(generateValidGetOrderAuditRequest()));
	}

	
	@Test
	void testTakeOrder_success() 
	{
	    when(orderRepository.findById(any())).thenReturn(Optional.of(generateValidOrderEntity()));
	    when(orderRepository.save(any())).thenReturn(easyRandom.nextObject(OrderEntity.class));
	    mockSecurityContext();
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
	
	@Test
	void testGetOrderDetails_null() {
	    when(orderRepository.findById(any())).thenReturn(Optional.empty());
	    assertNull(orderServiceImpl.getOrderDetail(1L));
	}
	
	// Private functions to aid testing
	private OrderDetails generateValidOrderDetails() {
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCreationDate(LocalDate.now());
		orderDetails.setDescription("Pickup for Carlos");
		orderDetails.setId(1L);
		orderDetails.setStatus(Status.UNASSIGNED);
		orderDetails.setVersion(1L);
		return orderDetails;
	}
	private OrderInputData generateValidOrderInputData() {
		OrderInputData orderInputData = new OrderInputData();
		OrderInputDataInputSearch inputSearch = new OrderInputDataInputSearch();
		inputSearch.setOrderId(1L);
		orderInputData.setInputSearch(inputSearch);
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

	private void mockSecurityContext() 
	{
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);
	    when(authentication.getName()).thenReturn("username");
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
		OrderInputAudit orderInputAudit = new OrderInputAudit();
		orderInputAudit.setAction(Action.CREATE);
		request.setOrderInputAudit(orderInputAudit);
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
	
	private GoogleOrderData generateValidGoogleOrderData() 
	{
		return GoogleOrderData.builder()
				.destinationAddress("address 1")
				.originAddress("address 2")
				.time("1 min")
				.build();
	}
}
