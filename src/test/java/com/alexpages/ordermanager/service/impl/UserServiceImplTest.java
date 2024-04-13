package com.alexpages.ordermanager.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.api.domain.User.RoleEnum;
import com.alexpages.ordermanager.entity.UserEntity;
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
		user.setRole(RoleEnum.ADMIN);
		user.setUsername("username");
		when(repository.findByUsername(any())).thenReturn(Optional.empty());
		when(encoder.encode(any())).thenReturn("1234567");
		when(mapper.toUserEntity(any())).thenReturn(generateUserEntity());
		when(repository.save(any())).thenReturn(generateUserEntity());
		assertNotNull(service.addUser(user));
	}
	
	@Test
	void testAddUsser_error() 
	{
		assertThrows(OrderManagerException500.class, () -> service.addUser(generateUser()));
	}
	
	@Test
	void testAddUsser_isPresent()
	{
		when(repository.findByUsername(any())).thenReturn(Optional.of(easyRandom.nextObject(UserEntity.class)));
		assertThrows(OrderManagerException500.class, () -> service.addUser(generateUser()));
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
	
	private UserEntity generateUserEntity() 
	{
		return UserEntity.builder()
				.password("1234567")
				.id(1L)
				.role(RoleEnum.ADMIN.getValue())
				.username("username")
				.build();
	}
	
	private User generateUser()
	{
		User user = new User();
		user.setPassword("1234567");
		user.setRole(RoleEnum.ADMIN);
		user.setUsername("username");
		return user;
	}
}