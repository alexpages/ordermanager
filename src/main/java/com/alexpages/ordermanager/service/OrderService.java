package com.alexpages.ordermanager.service;

import com.alexpages.ordermanager.domain.OrderListResponse;
import com.alexpages.ordermanager.domain.PlaceOrderRequest;
import com.alexpages.ordermanager.domain.PlaceOrderResponse;
import com.alexpages.ordermanager.domain.TakeOrderRequest;
import com.alexpages.ordermanager.domain.TakeOrderResponse;

import lombok.NonNull;

public interface OrderService 
{

	public PlaceOrderResponse placeOrder(@NonNull PlaceOrderRequest placeOrderRequest);

	public TakeOrderResponse takeOrder(@NonNull Long orderId, @NonNull TakeOrderRequest takeOrderRequest);

	public OrderListResponse getOrderList(@NonNull Integer page, @NonNull Integer limit);
	
}
