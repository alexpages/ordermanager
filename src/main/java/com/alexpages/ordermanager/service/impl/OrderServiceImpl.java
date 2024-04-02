package com.alexpages.ordermanager.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexpages.ordermanager.domain.OrderInputData;
import com.alexpages.ordermanager.domain.OrderListResponse;
import com.alexpages.ordermanager.domain.OrderOuputData;
import com.alexpages.ordermanager.domain.OrderPatchResponse;
import com.alexpages.ordermanager.domain.OrderPostRequest;
import com.alexpages.ordermanager.domain.OrderPostResponse;
import com.alexpages.ordermanager.domain.PaginationBody;
import com.alexpages.ordermanager.domain.Status;
import com.alexpages.ordermanager.domain.TakeOrderByIdRequest;
import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.enums.OrderStatusEnum;
import com.alexpages.ordermanager.error.OrderManagerException400;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.error.OrderManagerException409;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.mapper.OrderMapper;
import com.alexpages.ordermanager.repository.OrderRepository;
import com.alexpages.ordermanager.service.OrderService;
import com.alexpages.ordermanager.utils.PageableUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final GoogleMapsServiceImpl googleMapsServiceImpl;
	private final OrderMapper orderMapper;

	private static final String LATITUDE_PATTERN = "^(\\+|-)?(?:90(?:(?:\\.0{1,7})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,7})?))$";
	private static final String LONGITUDE_PATTERN = "^(\\+|-)?(?:180(?:(?:\\.0{1,7})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,7})?))$";

	@Transactional
	@Override
	public OrderPostResponse postOrder(@NonNull OrderPostRequest orderPostRequest) {
		try {
			log.info("OrderServiceImpl > placeOrder > PlaceOrderRequest: {}", orderPostRequest);
			validatePlaceOrderRequest(orderPostRequest);
			log.info("OrderServiceImpl > placeOrder > PlaceOrderRequest validated correctly");
			int distance = googleMapsServiceImpl.getDistanceFromDistanceMatrix(orderPostRequest);
			log.info("OrderServiceImpl > placeOrder > Distance calculated: {}", distance);

			OrderEntity savedEntity = orderRepository.save(OrderEntity.builder().distance(distance).status("UNASSIGNED").build());
			
			OrderPostResponse response = new OrderPostResponse();
			response.setDistance(savedEntity.getDistance());
			response.setOrderId(savedEntity.getId());
			response.setStatus(Status.fromValue(savedEntity.getStatus()));
			return response;
			
		} catch (Exception e) {
			log.error("OrderServiceImpl > placeOrder > There was an issue placing the order: {}", e);
			throw new OrderManagerException500("OrderServiceImpl > placeOrder > There was an issue placing the order: [" + e.getMessage() + "]");
		}
	}

	@Override
	public OrderOuputData getOrderList(OrderInputData orderInputData) {
		try {
			Pageable pageable = PageableUtils.getPageable(orderInputData.getPaginationBody());
			Page<OrderEntity> pOrderEntityPageable = orderRepository.findAll(pageable);
			log.info("OrderServiceImpl > listOrders > Page found: {}", pOrderEntityPageable);
			
			OrderOuputData response = new OrderOuputData();
			response.setOrders(orderMapper.toOrder(pOrderEntityPageable.getContent()));
			return response;
			
		} catch (Exception e) {
			log.error("OrderServiceImpl > listOrders > It could not get the list of orders: [" + e.getMessage() + "]");
			throw new OrderManagerException500("OrderServiceImpl > listOrders > Order list could not get retrieved, Exception: [" + e.getMessage() + "]");
		}
	}

	@Transactional
	@Override
	public OrderPatchResponse takeOrder(@NonNull Long orderId, @NonNull TakeOrderByIdRequest takeOrderByIdRequest) {
		try {
			if (!Status.TAKEN.getValue().equals(takeOrderByIdRequest.getStatus().getValue())) {
				log.error("Order status not valid: {}", takeOrderByIdRequest.getStatus());
				throw new OrderManagerException400(	"The provided order status is not valid: [" + takeOrderByIdRequest.getStatus() + "]");
			}
			Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
			if (!orderEntityOptional.isPresent()) {
				log.error("OrderServiceImpl > takeOrder > Order with ID: [" + orderId + "] was not found");
				throw new OrderManagerException404("Order with ID: [" + orderId + "] was not found");
			}
			OrderEntity orderEntity = orderEntityOptional.get();
			if (Status.TAKEN.getValue().equals((orderEntity.getStatus()))) {
				log.error("Order with ID: [" + orderId + "] was already taken");
				throw new OrderManagerException409("Order with ID: [" + orderId + "] was already taken");
			}
			orderEntity.setStatus(Status.TAKEN.getValue());
			orderRepository.save(orderEntity);
			OrderPatchResponse response = new OrderPatchResponse();
			response.setStatus(Status.SUCCESS.getValue());
			return response;

		} catch (OptimisticLockingFailureException e) {
			log.error("OrderServiceImpl > takeOrder > Order with ID: [" + orderId + "] was locked by another user");
			throw new OrderManagerException409("Order with ID: [" + orderId + "] was locked by another user");
		}
	}

	private void validatePlaceOrderRequest(OrderPostRequest orderPostRequest) {
 		if (!orderPostRequest.getCoordinates().getOrigin().get(0).matches(LATITUDE_PATTERN)	|| !orderPostRequest.getCoordinates().getOrigin().get(1).matches(LONGITUDE_PATTERN)) {
			throw new OrderManagerException400("Orgin coordinates are incorrect");
		}
		if (!orderPostRequest.getCoordinates().getDestination().get(0).matches(LATITUDE_PATTERN) || !orderPostRequest.getCoordinates().getDestination().get(1).matches(LONGITUDE_PATTERN)) {
			throw new OrderManagerException400("Destination coordinates are incorrect");
		}
	}
}