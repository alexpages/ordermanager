package com.alexpages.ordermanager.service;

import com.alexpages.ordermanager.domain.GetOrderAuditRequest;
import com.alexpages.ordermanager.domain.OrderInputData;
import com.alexpages.ordermanager.domain.OrderOuputData;
import com.alexpages.ordermanager.domain.OrderOutputAudit;
import com.alexpages.ordermanager.domain.OrderPatchInput;
import com.alexpages.ordermanager.domain.OrderPatchResponse;
import com.alexpages.ordermanager.domain.OrderPostRequest;
import com.alexpages.ordermanager.domain.OrderPostResponse;

import jakarta.validation.Valid;
import lombok.NonNull;

public interface OrderService 
{

	public OrderPostResponse postOrder(@NonNull OrderPostRequest orderPostRequest);

	public OrderOuputData getOrderList(OrderInputData orderInputData);

	public void deleteOrderById(@NonNull Long orderId);

	public OrderPatchResponse takeOrder(@NonNull Long orderId, @NonNull OrderPatchInput orderPatchInput);

	public OrderOutputAudit getAuditList(@Valid GetOrderAuditRequest getOrderAuditRequest);
	
}
