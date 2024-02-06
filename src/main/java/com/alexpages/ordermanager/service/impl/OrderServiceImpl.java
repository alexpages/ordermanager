package com.alexpages.ordermanager.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexpages.ordermanager.domain.OrderListResponse;
import com.alexpages.ordermanager.domain.PaginationBody;
import com.alexpages.ordermanager.domain.PlaceOrderRequest;
import com.alexpages.ordermanager.domain.PlaceOrderResponse;
import com.alexpages.ordermanager.domain.TakeOrderRequest;
import com.alexpages.ordermanager.domain.TakeOrderResponse;
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
	public PlaceOrderResponse placeOrder(@NonNull PlaceOrderRequest placeOrderRequest) {
		try {
			log.info("OrderServiceImpl > placeOrder > PlaceOrderRequest: {}", placeOrderRequest);
			validatePlaceOrderRequest(placeOrderRequest);
			log.info("OrderServiceImpl > placeOrder > PlaceOrderRequest validated correctly");
			int distance = googleMapsServiceImpl.getDistanceFromDistanceMatrix(placeOrderRequest);
			log.info("OrderServiceImpl > placeOrder > Distance calculated: {}", distance);

			OrderEntity savedEntity = orderRepository.save(OrderEntity.builder().distance(distance).status("UNASSIGNED").build());
			return PlaceOrderResponse.builder()
					.distance(savedEntity.getDistance())
					.id(savedEntity.getId())
					.status(savedEntity.getStatus())
					.build();
			
		} catch (Exception e) {
			log.error("OrderServiceImpl > placeOrder > There was an issue placing the order: {}", e);
			throw new OrderManagerException500("OrderServiceImpl > placeOrder > There was an issue placing the order: [" + e.getMessage() + "]");
		}
	}

	@Override
	public OrderListResponse getOrderList(@NonNull Integer page, @NonNull Integer limit) {
		try {
			log.info("OrderServiceImpl > listOrders > page: {}, limit: {}", page, limit);
			PaginationBody paginationBody = new PaginationBody();
			paginationBody.setPage(new BigDecimal(page));
			paginationBody.setSize(new BigDecimal(limit));
			Pageable pageable = PageableUtils.getPageable(paginationBody);
			Page<OrderEntity> pOrderEntityPageable = orderRepository.findAll(pageable);
			log.info("OrderServiceImpl > listOrders > Page found: {}", pOrderEntityPageable);
			
			return OrderListResponse.builder()
					.orders(orderMapper.toOrderDTOList(pOrderEntityPageable.getContent()))
					.build();
			
		} catch (Exception e) {
			log.error("OrderServiceImpl > listOrders > It could not get the list of orders: [" + e.getMessage() + "]");
			throw new OrderManagerException500("OrderServiceImpl > listOrders > Order list could not get retrieved, Exception: [" + e.getMessage() + "]");
		}
	}

	@Transactional
	@Override
	public TakeOrderResponse takeOrder(@NonNull Long orderId, @NonNull TakeOrderRequest takeOrderRequest) {
		try {
			if (!OrderStatusEnum.TAKEN.getValue().equals(takeOrderRequest.getStatus())) {
				log.error("Order status not valid: {}", takeOrderRequest.getStatus());
				throw new OrderManagerException400(	"The provided order status is not valid: [" + takeOrderRequest.getStatus() + "]");
			}
			Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
			if (!orderEntityOptional.isPresent()) {
				log.error("OrderServiceImpl > takeOrder > Order with ID: [" + orderId + "] was not found");
				throw new OrderManagerException404("Order with ID: [" + orderId + "] was not found");
			}
			OrderEntity orderEntity = orderEntityOptional.get();
			if (OrderStatusEnum.TAKEN.getValue().equals((orderEntity.getStatus()))) {
				log.error("Order with ID: [" + orderId + "] was already taken");
				throw new OrderManagerException409("Order with ID: [" + orderId + "] was already taken");
			}
			orderEntity.setStatus(OrderStatusEnum.TAKEN.getValue());
			orderRepository.save(orderEntity);
			return TakeOrderResponse.builder().status(OrderStatusEnum.SUCCESS.getValue()).build();

		} catch (OptimisticLockingFailureException e) {
			log.error("OrderServiceImpl > takeOrder > Order with ID: [" + orderId + "] was locked by another user");
			throw new OrderManagerException409("Order with ID: [" + orderId + "] was locked by another user");
		}
	}

	private void validatePlaceOrderRequest(PlaceOrderRequest placeOrderRequest) {
 		if (!placeOrderRequest.getOrigin()[0].matches(LATITUDE_PATTERN)	|| !placeOrderRequest.getOrigin()[1].matches(LONGITUDE_PATTERN)) {
			throw new OrderManagerException400("Orgin coordinates are incorrect");
		}
		if (!placeOrderRequest.getDestination()[0].matches(LATITUDE_PATTERN) || !placeOrderRequest.getDestination()[1].matches(LONGITUDE_PATTERN)) {
			throw new OrderManagerException400("Destination coordinates are incorrect");
		}
	}
}