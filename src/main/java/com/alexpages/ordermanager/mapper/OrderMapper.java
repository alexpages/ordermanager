package com.alexpages.ordermanager.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.alexpages.ordermanager.domain.Order;
import com.alexpages.ordermanager.domain.OrderDTO;
import com.alexpages.ordermanager.entity.OrderEntity;

import jakarta.validation.Valid;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	
	List<OrderDTO> toOrderDTOList(List<OrderEntity> orderEntities);
	
	OrderDTO toOrderDTO(OrderEntity orderEntity);

	List<@Valid Order> toOrder(List<OrderEntity> content);
	
}
