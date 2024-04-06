package com.alexpages.ordermanager.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.alexpages.ordermanager.api.OrdersApi;
import com.alexpages.ordermanager.api.domain.GetOrderAuditRequest;
import com.alexpages.ordermanager.api.domain.OrderDetailResponse;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.OrderInputData;
import com.alexpages.ordermanager.api.domain.OrderOutputAudit;
import com.alexpages.ordermanager.api.domain.OrderOutputData;
import com.alexpages.ordermanager.api.domain.OrderPatchInput;
import com.alexpages.ordermanager.api.domain.OrderPatchResponse;
import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.api.domain.OrderPostResponse;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.error.OrderManagerException400;
import com.alexpages.ordermanager.service.impl.OrderServiceImpl;
import com.alexpages.ordermanager.utils.ListUtils;

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
	public ResponseEntity<OrderPatchResponse> takeOrderById(Long orderId, @Valid OrderPatchInput orderPatchInput) {
		OrderPatchResponse response = orderServiceImpl.takeOrder(orderId, orderPatchInput);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<OrderOutputData> postOrdersRequest(OrderInputData orderInputData) 
	{
		PaginationBody pagination = orderInputData.getPaginationBody();
		if (pagination.getPage().intValue() < 1) {
			throw new OrderManagerException400("Page number must start with 1");
		}
		if (pagination.getSize().intValue() <= 0) {
			throw new OrderManagerException400("Limit should be a positive integer higher than 0");
		}
		OrderOutputData response = orderServiceImpl.getOrderList(orderInputData);
		if (ListUtils.isBlank(response.getOrders())){
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

	@Override
	public ResponseEntity<OrderOutputAudit> getOrderAudit(@Valid GetOrderAuditRequest getOrderAuditRequest) 
	{
		PaginationBody pagination = getOrderAuditRequest.getPaginationBody();
		if (pagination.getPage().intValue() < 1) {
			throw new OrderManagerException400("Page number must start with 1");
		}
		if (pagination.getSize().intValue() <= 0) {
			throw new OrderManagerException400("Limit should be a positive integer higher than 0");
		}
		OrderOutputAudit response = orderServiceImpl.getAuditList(getOrderAuditRequest);
		if (ListUtils.isBlank(response.getOrders())){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
}