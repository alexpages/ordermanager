package com.alexpages.ordermanager.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.alexpages.ordermanager.api.domain.Role;
import com.alexpages.ordermanager.entity.UserEntity;
import com.alexpages.ordermanager.security.UserInfoDetails;

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTest {
	
	private JwtServiceImpl service;
	
	@BeforeEach
	void setUp() {
		service = new JwtServiceImpl();
        ReflectionTestUtils.setField(service, "key", "javax.crypto.spec.SecretKeySpec@5881a62"); //for test
	}

	@Test
	void testTokenAndUserFlow_success() {
		String token = service.generateToken("username");	
		assertEquals("username", service.extractUsername(token));
		assertNotNull(service.extractExpiration(token));
		assertEquals(Boolean.TRUE,service.validateToken(token, generateValidInfoDetails()));
		assertEquals(Boolean.FALSE,service.validateToken(service.generateToken("otherUsername"), generateValidInfoDetails()));
	}
	
	private UserInfoDetails generateValidInfoDetails() {
		return new UserInfoDetails(generateValidUserEntity());
	}
	
	private UserEntity generateValidUserEntity() {
		return UserEntity.builder()
				.password("password")
				.username("username")
				.role(Role.ADMIN.getValue())
				.build();
	}
}
