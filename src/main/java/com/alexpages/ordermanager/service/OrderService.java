package com.alexpages.ordermanager.service;

import javax.validation.Valid;

import com.alexpages.ordermanager.api.domain.GetOrderAuditRequest;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.OrderInputData;
import com.alexpages.ordermanager.api.domain.OrderOutputAudit;
import com.alexpages.ordermanager.api.domain.OrderOutputData;
import com.alexpages.ordermanager.api.domain.OrderPatchInput;
import com.alexpages.ordermanager.api.domain.OrderPatchResponse;
import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.api.domain.OrderPostResponse;

import lombok.NonNull;

public interface OrderService 
{

	public OrderPostResponse postOrder(@NonNull OrderPostRequest orderPostRequest);

	public OrderOutputData getOrders(OrderInputData orderInputData);

	public void deleteOrderById(@NonNull Long orderId);

	public OrderPatchResponse takeOrder(@NonNull Long orderId, @NonNull OrderPatchInput orderPatchInput);

	public OrderOutputAudit getAuditList(@Valid GetOrderAuditRequest getOrderAuditRequest);

	public OrderDetails getOrderDetail(@NonNull Long orderId);
	
}
