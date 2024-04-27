package com.alexpages.ordermanager.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.alexpages.ordermanager.api.domain.OrderAudit;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.Role;
import com.alexpages.ordermanager.api.domain.Status;
import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.entity.OrderAuditEntity;
import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface OrderManagerMapper {

	OrderManagerMapper INSTANCE = Mappers.getMapper(OrderManagerMapper.class);

	//Order related
	
	@Mapping(target = "creationDate", source = "creationDate", qualifiedByName = "localDateTimeToLocalDate")
	List<@Valid OrderDetails> toOrderList(List<OrderEntity> content);
	
	@Mapping(target = "creationDate", source = "creationDate", qualifiedByName = "localDateTimeToLocalDate")
	@Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
	OrderDetails toOrderDetails(OrderEntity content);

	@Mapping(target = "actionDate", source = "actionDate", qualifiedByName = "localDateTimeToLocalDate")
	List<OrderAudit> toOrderAuditList(List<OrderAuditEntity> content);
	
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
	
	// User related
	@Mapping(target = "role", source = "role", qualifiedByName = "stringToRoleEnum")
	User toUser(UserEntity userEntity);
	
	@Mapping(target = "role", source = "role", qualifiedByName = "roleEnumToString")
	UserEntity toUserEntity(User user);

	@Named("roleEnumToString")
	default String roleEnumToString(Role role)
	{
		return role.getValue(); // no need to check for null
	}
	
	@Named("stringToRoleEnum")
	default Role stringToRoleEnum(String role)
	{
		return Role.fromValue(role); // no need to check for null
	}

	List<User> toUserList(List<UserEntity> content);



}