package com.alexpages.ordermanager.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.alexpages.ordermanager.api.OrdersApi;
import com.alexpages.ordermanager.domain.GetOrderAuditRequest;
import com.alexpages.ordermanager.domain.OrderDetailResponse;
import com.alexpages.ordermanager.domain.OrderInputData;
import com.alexpages.ordermanager.domain.OrderOuputData;
import com.alexpages.ordermanager.domain.OrderOutputAudit;
import com.alexpages.ordermanager.domain.OrderPatchResponse;
import com.alexpages.ordermanager.domain.OrderPostRequest;
import com.alexpages.ordermanager.domain.OrderPostResponse;
import com.alexpages.ordermanager.domain.TakeOrderByIdRequest;
import com.alexpages.ordermanager.service.impl.OrderServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrdersApi {

	private final OrderServiceImpl orderServiceImpl;

	@Override
	public ResponseEntity<OrderPostResponse> postOrder(@Valid OrderPostRequest orderPostRequest) {
		OrderPostResponse response = orderServiceImpl.postOrder(orderPostRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<OrderPatchResponse> takeOrderById(Long orderId, @Valid TakeOrderByIdRequest takeOrderByIdRequest) {
		OrderPatchResponse response = orderServiceImpl.takeOrder(orderId, takeOrderByIdRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Void> deleterOrderById(Long orderId) 
	{
		return null;
	}

	@Override
	public ResponseEntity<OrderOutputAudit> getOrderAudit(@Valid GetOrderAuditRequest getOrderAuditRequest) {
		return null;
	}

	@Override
	public ResponseEntity<OrderDetailResponse> getOrderById(Long orderId) 
	{
		return null;
	}



	@Override
	public ResponseEntity<OrderOuputData> postOrdersRequest(@Valid OrderInputData orderInputData) 
	{
		
		return null;
	}


//
//	@Override
//	public ResponseEntity<List<OrderDTO>> getOrderList(int page, int limit) {
//		if (page < 1) {
//			throw new OrderManagerException400("Page number must start with 1");
//		}
//		if (limit <= 0) {
//			throw new OrderManagerException400("Limit should be a positive integer higher than 0");
//		}
//		OrderListResponse orderListResponse = orderServiceImpl.getOrderList(page, limit);
//		return new ResponseEntity<>(orderListResponse.getOrders(), HttpStatus.OK);
//	}
	
	
}