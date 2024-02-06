package com.alexpages.ordermanager.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.alexpages.ordermanager.domain.OrderDTO;
import com.alexpages.ordermanager.entity.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	
	List<OrderDTO> toOrderDTOList(List<OrderEntity> orderEntities);
	
	OrderDTO toOrderDTO(OrderEntity orderEntity);
	
}
