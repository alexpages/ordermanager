package com.alexpages.ordermanager.api;

import com.alexpages.ordermanager.domain.OrderDTO;
import com.alexpages.ordermanager.domain.PlaceOrderRequest;
import com.alexpages.ordermanager.domain.PlaceOrderResponse;
import com.alexpages.ordermanager.domain.TakeOrderRequest;
import com.alexpages.ordermanager.domain.TakeOrderResponse;
import com.alexpages.ordermanager.error.OrderManagerException500;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
public interface OrderApi {

	@PostMapping(value = { "/orders" }, produces = { "application/json" }, consumes = { "application/json" })
	ResponseEntity<PlaceOrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest placeOrderRequest) 
	throws OrderManagerException500;

	@PatchMapping(value = { "/orders/{id}" }, produces = { "application/json" }, consumes = { "application/json" })
	ResponseEntity<TakeOrderResponse> takeOrder(@PathVariable("id") Long orderId, @Valid @RequestBody TakeOrderRequest takeOrderRequest)
	throws OrderManagerException500;

	@GetMapping(value = { "/orders" }, produces = { "application/json" })
	ResponseEntity<List<OrderDTO>> getOrderList(@RequestParam("page") int page, @RequestParam("limit") int limit)
	throws OrderManagerException500;
}