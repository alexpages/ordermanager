package com.alexpages.ordermanager.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.alexpages.ordermanager.domain.OrderDetails;
import com.alexpages.ordermanager.entity.OrderEntity;

import jakarta.validation.Valid;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

	@Mapping(target = "creationDate", source = "creationDate", qualifiedByName = "localDateTimeToLocalDate")
	List<@Valid OrderDetails> toOrderList(List<OrderEntity> content);

	@Named("localDateTimeToLocalDate")
	default LocalDate localDateTimeToLocalDate(LocalDateTime localDatetime)
	{
		return localDatetime.toLocalDate();
	}
}
