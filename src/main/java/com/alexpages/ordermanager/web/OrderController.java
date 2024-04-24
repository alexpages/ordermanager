package com.alexpages.ordermanager.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.alexpages.ordermanager.api.OrdersApi;
import com.alexpages.ordermanager.api.domain.GetOrderAuditRequest;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.OrderInputData;
import com.alexpages.ordermanager.api.domain.OrderOutputAudit;
import com.alexpages.ordermanager.api.domain.OrderOutputData;
import com.alexpages.ordermanager.api.domain.OrderPatchInput;
import com.alexpages.ordermanager.api.domain.OrderPatchResponse;
import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.api.domain.OrderPostResponse;
import com.alexpages.ordermanager.service.impl.OrderServiceImpl;
import com.alexpages.ordermanager.utils.ListUtils;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {

	private final OrderServiceImpl orderServiceImpl;

	@Override
	public ResponseEntity<OrderPostResponse> postOrder(@Valid OrderPostRequest orderPostRequest) {
		OrderPostResponse response = orderServiceImpl.postOrder(orderPostRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<OrderPatchResponse> takeOrderById(Long orderId, @Valid OrderPatchInput orderPatchInput) {
		OrderPatchResponse response = orderServiceImpl.takeOrder(orderId, orderPatchInput);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<OrderOutputData> getOrders(OrderInputData orderInputData) 
	{
		OrderOutputData response = orderServiceImpl.getOrders(orderInputData);
		if (response == null){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<OrderOutputAudit> getOrderAudit(@Valid GetOrderAuditRequest getOrderAuditRequest) 
	{
		OrderOutputAudit response = orderServiceImpl.getAuditList(getOrderAuditRequest);
		if (response == null){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Void> deleterOrderById(Long orderId)
	{
		orderServiceImpl.deleteOrderById(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@Override
	public ResponseEntity<OrderDetails> getOrderById(Long orderId) 
	{
		OrderDetails orderDetails = orderServiceImpl.getOrderDetail(orderId);
		if (orderDetails != null) {
			return new ResponseEntity<>(orderDetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}