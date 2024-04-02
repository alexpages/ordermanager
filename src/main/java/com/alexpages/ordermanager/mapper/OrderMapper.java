package com.alexpages.ordermanager.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.alexpages.ordermanager.domain.Order;
import com.alexpages.ordermanager.entity.OrderEntity;

import jakarta.validation.Valid;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	
	List<@Valid Order> toOrder(List<OrderEntity> content);
	
}
