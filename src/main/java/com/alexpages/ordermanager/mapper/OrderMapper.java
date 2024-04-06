package com.alexpages.ordermanager.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.Status;
import com.alexpages.ordermanager.entity.OrderEntity;

import jakarta.validation.Valid;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

	@Mapping(target = "creationDate", source = "creationDate", qualifiedByName = "localDateTimeToLocalDate")
	List<@Valid OrderDetails> toOrderList(List<OrderEntity> content);
	
	@Mapping(target = "creationDate", source = "creationDate", qualifiedByName = "localDateTimeToLocalDate")
	@Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
	OrderDetails toOrderDetails(OrderEntity content);

	@Named("localDateTimeToLocalDate")
	default LocalDate localDateTimeToLocalDate(LocalDateTime localDatetime)
	{
		return localDatetime.toLocalDate();
	}
	
	@Named("stringToStatus")
	default Status stringToStatus(String status)
	{
		return Status.fromValue(status); // no need to check for null
	}
}
