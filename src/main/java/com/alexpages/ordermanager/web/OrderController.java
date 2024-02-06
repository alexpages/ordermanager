package com.alexpages.ordermanager.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alexpages.ordermanager.api.OrderApi;
import com.alexpages.ordermanager.domain.OrderDTO;
import com.alexpages.ordermanager.domain.OrderListResponse;
import com.alexpages.ordermanager.domain.PlaceOrderRequest;
import com.alexpages.ordermanager.domain.PlaceOrderResponse;
import com.alexpages.ordermanager.domain.TakeOrderRequest;
import com.alexpages.ordermanager.domain.TakeOrderResponse;
import com.alexpages.ordermanager.error.OrderManagerException400;
import com.alexpages.ordermanager.service.impl.OrderServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrderApi {

	private final OrderServiceImpl orderServiceImpl;

	@Override
	public ResponseEntity<PlaceOrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest placeOrderRequest){
		PlaceOrderResponse placeOrderResponse = orderServiceImpl.placeOrder(placeOrderRequest);
		return new ResponseEntity<>(placeOrderResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TakeOrderResponse> takeOrder(Long orderId, @Valid @RequestBody TakeOrderRequest takeOrderRequest) {
		TakeOrderResponse takeOrderResponse = orderServiceImpl.takeOrder(orderId, takeOrderRequest);
		return new ResponseEntity<>(takeOrderResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrderDTO>> getOrderList(int page, int limit) {
		if (page < 1) {
			throw new OrderManagerException400("Page number must start with 1");
		}
		if (limit <= 0) {
			throw new OrderManagerException400("Limit should be a positive integer higher than 0");
		}
		OrderListResponse orderListResponse = orderServiceImpl.getOrderList(page, limit);
		return new ResponseEntity<>(orderListResponse.getOrders(), HttpStatus.OK);
	}
}