package com.alexpages.ordermanager.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTest {
	
	private JwtServiceImpl service;
	
	@BeforeEach
	void setUp()
	{
		service = new JwtServiceImpl();
	}

	@Test
	void testGenerateToken_success() {
		assertNotNull(service.generateToken("username"));	
	}
	
//   @Test
//	void testExtractUsername_success() {
//	   service = spy(service); 
//	   String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYSIsImlhdCI6MTcxMzc2NjgwNiwiZXhwIjoxNzEzNzY4NjA2fQ.DwopG_N5p2g7hzghfat4KQlf4nEbPdHjF2rAWnhIWMg";
//	   when(service.extractClaim(token, Claims::getSubject)).thenReturn("username");
//	   String username = service.extractUsername(token);
//	   assertEquals("username", username);
//    }
	

}
