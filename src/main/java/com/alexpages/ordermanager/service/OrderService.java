package com.alexpages.ordermanager.service;

import com.alexpages.ordermanager.domain.OrderInputData;
import com.alexpages.ordermanager.domain.OrderListResponse;
import com.alexpages.ordermanager.domain.OrderOuputData;
import com.alexpages.ordermanager.domain.OrderPatchResponse;
import com.alexpages.ordermanager.domain.OrderPostRequest;
import com.alexpages.ordermanager.domain.OrderPostResponse;
import com.alexpages.ordermanager.domain.TakeOrderByIdRequest;

import lombok.NonNull;

public interface OrderService 
{

	public OrderPostResponse postOrder(@NonNull OrderPostRequest orderPostRequest);

	public OrderOuputData getOrderList(OrderInputData orderInputData);

	public OrderPatchResponse takeOrder(@NonNull Long orderId, @NonNull TakeOrderByIdRequest takeOrderByIdRequest);

}
