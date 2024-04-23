package com.alexpages.ordermanager.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexpages.ordermanager.api.domain.Action;
import com.alexpages.ordermanager.api.domain.GetOrderAuditRequest;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.OrderInputAudit;
import com.alexpages.ordermanager.api.domain.OrderInputData;
import com.alexpages.ordermanager.api.domain.OrderInputDataInputSearch;
import com.alexpages.ordermanager.api.domain.OrderOutputAudit;
import com.alexpages.ordermanager.api.domain.OrderOutputData;
import com.alexpages.ordermanager.api.domain.OrderPatchInput;
import com.alexpages.ordermanager.api.domain.OrderPatchResponse;
import com.alexpages.ordermanager.api.domain.OrderPostRequest;
import com.alexpages.ordermanager.api.domain.OrderPostResponse;
import com.alexpages.ordermanager.api.domain.Status;
import com.alexpages.ordermanager.entity.OrderAuditEntity;
import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.error.OrderManagerException400;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.error.OrderManagerException409;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.mapper.OrderManagerMapper;
import com.alexpages.ordermanager.repository.OrderAuditRepository;
import com.alexpages.ordermanager.repository.OrderRepository;
import com.alexpages.ordermanager.service.OrderService;
import com.alexpages.ordermanager.utils.DateUtils;
import com.alexpages.ordermanager.utils.ListUtils;
import com.alexpages.ordermanager.utils.PageableUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderAuditRepository orderAuditRepository;
	private final GoogleMapsServiceImpl googleMapsServiceImpl;
	private final OrderManagerMapper orderMapper;
	
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

			OrderEntity savedEntity = orderRepository.save(
					OrderEntity.builder()
					.distance(distance)
					.description(orderPostRequest.getDescription())
					.creationDate(LocalDateTime.now())
					.status("UNASSIGNED")
					.build());
			addOrderAuditEntity(savedEntity, Action.CREATE.getValue());
			
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
	@Transactional(readOnly = true)
	@Override
	public OrderOutputData getOrders(OrderInputData orderInputData) {
		final String LOG_PREFIX = "OrderServiceImpl > getOrders > ";
		try {
			Pageable pageable = PageableUtils.getPageable(orderInputData.getPaginationBody());
			OrderInputDataInputSearch inputSearch;
			
			if(orderInputData.getInputSearch() != null) {
				inputSearch = orderInputData.getInputSearch();
			} else {
				inputSearch = new OrderInputDataInputSearch();	// Object with nulls
			}
			log.info(LOG_PREFIX + "orderInputData: {}", orderInputData.toString());
			Page<OrderEntity> pageOrderEntity = orderRepository.filterByParams(
					inputSearch.getOrderId(),
					getStatus(inputSearch.getStatus()),
					DateUtils.toLocalDateTime(inputSearch.getStartCreationDate()),
					DateUtils.toLocalDateTime(inputSearch.getEndCreationDate()),
					pageable);
			
			if (!ListUtils.isBlank(pageOrderEntity.getContent())) {
			    log.info(LOG_PREFIX + "Orders: {}", pageOrderEntity.getContent());
				OrderOutputData response = new OrderOutputData();
				response.setOrders(orderMapper.toOrderList(pageOrderEntity.getContent()));
				response.setPageResponse(PageableUtils.getPaginationResponse(pageOrderEntity, pageOrderEntity.getPageable()));
				return response;
			} else {
			    log.error(LOG_PREFIX + "pageOrderEntity is null");
			    return null;
			}
		} catch (Exception e) {
			log.error(LOG_PREFIX + "It could not get the list of orders: [" + e.getMessage() + "]");
			throw new OrderManagerException500(LOG_PREFIX + "Order list could not get retrieved, Exception: [" + e.getMessage() + "]");
		}
	}
		
	@Transactional
	@Override
	public OrderPatchResponse takeOrder(@NonNull Long orderId, @NonNull OrderPatchInput orderPatchInput) {
			final String LOG_PREFIX = "OrderServiceImpl > takeOrder > ";
		try {
			Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
			if (!orderEntityOptional.isPresent()) {
				log.error(LOG_PREFIX + "Order with ID: [" + orderId + "] was not found");
				throw new OrderManagerException404("Order with ID: [" + orderId + "] was not found");
			
			} else {
				
				OrderEntity orderEntity = orderEntityOptional.get();
				if (orderEntity.getStatus().equals(orderPatchInput.getStatus().getValue())) {
					throw new OrderManagerException409("Order with ID: [" + orderId + "] already has the status: [" + orderPatchInput.getStatus() + "]");
				
				} else {
					orderEntity.setStatus(orderPatchInput.getStatus().getValue());
					OrderEntity savedEntity = orderRepository.save(orderEntity);
					addOrderAuditEntity(savedEntity, Action.UPDATE.getValue());			
					OrderPatchResponse response = new OrderPatchResponse();
					response.setStatus("SUCCESS");
					return response;
				}		
			}
		
		} catch (OptimisticLockingFailureException e) {
			log.error(LOG_PREFIX + "Order with ID: [" + orderId + "] was locked by another user");
			throw new OrderManagerException409("Order with ID: [" + orderId + "] was locked by another user");
		}
	}
	
	@Transactional
	@Override
	public void deleteOrderById(Long orderId) {
		Optional<OrderEntity> deletedEntity = orderRepository.findById(orderId);
	    if (!deletedEntity.isPresent()) {
			log.error("OrderServiceImpl > deleteOrderById > Order was not found for id: [" + orderId + "]");
	        throw new OrderManagerException404("Order with id: [" + orderId + "] was not found");
	    } else {
			addOrderAuditEntity(deletedEntity.get(), Action.DELETE.getValue());	
		    orderRepository.deleteById(orderId);	
	    }
	}
	
	@Transactional(readOnly = true)
	@Override
	public OrderOutputAudit getAuditList(GetOrderAuditRequest getOrderAuditRequest) {
		final String LOG_PREFIX = "OrderServiceImpl > listOrders > ";
		try {
			Pageable pageable = PageableUtils.getPageable(getOrderAuditRequest.getPaginationBody());
			OrderInputAudit orderInputAudit;
			String action = null;
			
			if(getOrderAuditRequest.getOrderInputAudit() != null) {
				orderInputAudit = getOrderAuditRequest.getOrderInputAudit();
				if (orderInputAudit.getAction() != null) {
					action = orderInputAudit.getAction().getValue();
				}
			} else {
				orderInputAudit = new OrderInputAudit();	// Object with nulls
			}
			log.info(LOG_PREFIX  + "Orders: {}", orderInputAudit.toString());
			Page<OrderAuditEntity> pageOrderAuditEntity = orderAuditRepository.filterByParams(
					orderInputAudit.getOrderId(),
					action,
					DateUtils.toLocalDateTime(orderInputAudit.getStartDate()),
					DateUtils.toLocalDateTime(orderInputAudit.getEndDate()),
					pageable);
			
			if (pageOrderAuditEntity != null) {
			    log.info(LOG_PREFIX  + "Orders: {}", pageOrderAuditEntity.getContent());
				OrderOutputAudit response = new OrderOutputAudit();
				response.setOrders(orderMapper.toOrderAuditList(pageOrderAuditEntity.getContent()));
				response.setPageResponse(PageableUtils.getPaginationResponse(pageOrderAuditEntity, pageOrderAuditEntity.getPageable()));
				return response;
			
			} else {
			    log.error(LOG_PREFIX  + "pageOrderEntity is null");
			    return null;
			}
		} catch (Exception e) {
			log.error(LOG_PREFIX  + "It could not get the list of orders: [" + e.getMessage() + "]");
			throw new OrderManagerException500(LOG_PREFIX  + "Order list could not get retrieved, Exception: [" + e.getMessage() + "]");
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public OrderDetails getOrderDetail(Long orderId) 
	{
		Optional<OrderEntity> oOrderEntity = orderRepository.findById(orderId);
		if (oOrderEntity.isPresent()) {
			return orderMapper.toOrderDetails(oOrderEntity.get());
		}
		return null;
	}
	
	private OrderAuditEntity addOrderAuditEntity(OrderEntity orderEntity, String action) {
		try {
			OrderAuditEntity orderAuditEntity = createOrderAuditEntity(orderEntity, action);
			return orderAuditRepository.save(orderAuditEntity);
		} catch (Exception e) {
			log.error("OrderServiceImpl > addOrderAuditEntity > It could not save the order audit: [" + e.getMessage() + "]");
			throw new OrderManagerException500("OrderServiceImpl > addOrderAuditEntity > It could not save the order audit, Exception: [" + e.getMessage() + "]");
		}
	}
	
	private OrderAuditEntity createOrderAuditEntity(OrderEntity orderEntity, String action) 
	{
		return OrderAuditEntity.builder()
				.orderId(orderEntity.getId())
				.action(action)
				.username(SecurityContextHolder.getContext().getAuthentication().getName())
				.actionDate(LocalDateTime.now())
				.build();
	}

	private void validatePlaceOrderRequest(OrderPostRequest orderPostRequest) {
 		if (!orderPostRequest.getCoordinates().getOrigin().get(0).matches(LATITUDE_PATTERN)	|| !orderPostRequest.getCoordinates().getOrigin().get(1).matches(LONGITUDE_PATTERN)) {
			throw new OrderManagerException400("Orgin coordinates are incorrect");
		}
		if (!orderPostRequest.getCoordinates().getDestination().get(0).matches(LATITUDE_PATTERN) || !orderPostRequest.getCoordinates().getDestination().get(1).matches(LONGITUDE_PATTERN)) {
			throw new OrderManagerException400("Destination coordinates are incorrect");
		}
	}
	
	private String getStatus(Status status) {
		String statusValue = null;
		if (status != null) {
			statusValue = status.getValue();
		}
		return statusValue;
	}

}