package com.alexpages.ordermanager.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.alexpages.ordermanager.api.domain.Role;
import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.api.domain.UserInputData;
import com.alexpages.ordermanager.api.domain.UserInputDataInputSearch;
import com.alexpages.ordermanager.entity.UserEntity;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.error.OrderManagerException409;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.mapper.OrderManagerMapper;
import com.alexpages.ordermanager.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	@InjectMocks
	private UserServiceImpl service;
	@Mock
	private UserRepository repository;
	@Mock
    private PasswordEncoder encoder; 
	@Mock
    private OrderManagerMapper mapper;

	private EasyRandom easyRandom;

	@BeforeEach
	void setUp() 
	{
		easyRandom = new EasyRandom();
	}

	@Test
	void testAddUsser_success() 
	{
		User user = new User();
		user.setPassword("1234567");
		user.setRole(Role.ADMIN);
		user.setUsername("username");
		when(repository.findByUsername(any())).thenReturn(Optional.empty());
		when(encoder.encode(any())).thenReturn("1234567");
		when(mapper.toUserEntity(any())).thenReturn(generateUserEntity());
		when(repository.save(any())).thenReturn(generateUserEntity());
		assertNotNull(service.addUser(user));
	}

	@Test
	void testAddUsser_isPresent()
	{
		when(repository.findByUsername(any())).thenReturn(Optional.of(easyRandom.nextObject(UserEntity.class)));
		assertThrows(OrderManagerException409.class, () -> service.addUser(generateUser()));
	}
	
	@Test
	void testLoadUserByUsername_success() 
	{
		when(repository.findByUsername(any())).thenReturn(Optional.of(generateUserEntity() ));
		assertNotNull(service.loadUserByUsername("username"));
	}
	
	@Test
	void testLoadUserByUsername_error() 
	{
	    when(repository.findByUsername(any())).thenReturn(Optional.empty());
	    assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
	}	
	
	@Test
	void testGetUsers_success() 
	{
	    List<UserEntity> lUserEntities = new ArrayList<>();
	    lUserEntities.add(generateUserEntity());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(repository.filterByParams(any(), any(), any(), any())).thenReturn(new PageImpl<>(lUserEntities, pageable, lUserEntities.size()));
	    assertNotNull(service.getUsers(generateValidUserInputData()));
	}
	
	@Test
	void testGetUsers_nullinput() 
	{
	    List<UserEntity> lUserEntities = new ArrayList<>();
	    lUserEntities.add(generateUserEntity());
	    Pageable pageable = PageRequest.of(1, 10);
	    when(repository.filterByParams(any(), any(), any(), any())).thenReturn(new PageImpl<>(lUserEntities, pageable, lUserEntities.size()));
	    assertNotNull(service.getUsers(new UserInputData()));
	}
	
	@Test
	void testGetUsers_withRole() 
	{
	    when(repository.filterByParams(any(), any(), any(), any())).thenThrow(new RuntimeException("some error"));
	    
	    UserInputData input = new UserInputData();
	    UserInputDataInputSearch inputSearch = new UserInputDataInputSearch();
	    inputSearch.setRole(Role.ADMIN);
	    input.setInputSearch(inputSearch);
	    assertThrows(OrderManagerException500.class, () -> service.getUsers(input));
	}
	
	@Test
	void testGetUsers_error() 
	{
	    when(repository.filterByParams(any(), any(), any(), any())).thenThrow(new RuntimeException("some error"));
	    assertThrows(OrderManagerException500.class, () -> service.getUsers(generateValidUserInputData()));
	}
	
	@Test
	void testDeleteUser_success() 
	{
		when(repository.findById(any())).thenReturn(Optional.of(new UserEntity()));
	    assertDoesNotThrow(() -> service.deleteUserById(1L));
	}
	
	@Test
	void testDeleteUser_error() 
	{
		assertThrows(OrderManagerException404.class, () -> service.deleteUserById(1L));
	}
	
	@Test
	void testGetUser_success() 
	{
		when(repository.findById(any())).thenReturn(Optional.of(generateUserEntity()));
		when(mapper.toUser(any())).thenReturn(generateUser());
		assertNotNull(service.getUserById(1L));
	}
	
	@Test
	void testGetUser_error() 
	{
	    assertNull(service.getUserById(1L));
	}	

	// Private methods
	private UserInputData generateValidUserInputData() 
	{
		UserInputData userInputData = new UserInputData();
		UserInputDataInputSearch inputSearch = new UserInputDataInputSearch();
		userInputData.setInputSearch(inputSearch);
		PaginationBody pagination = new PaginationBody();
		pagination.setPage(new BigDecimal(2));
		pagination.setSize(new BigDecimal(10));
		userInputData.setPaginationBody(pagination);
		return userInputData;
	}
	
	private UserEntity generateUserEntity() 
	{
		return UserEntity.builder()
				.password("1234567")
				.id(1L)
				.role(Role.ADMIN.getValue())
				.username("username")
				.build();
	}
	
	private User generateUser()
	{
		User user = new User();
		user.setPassword("1234567");
		user.setRole(Role.ADMIN);
		user.setUsername("username");
		return user;
	}
}