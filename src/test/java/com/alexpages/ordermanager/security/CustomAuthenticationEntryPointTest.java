package com.alexpages.ordermanager.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationEntryPointTest {

	@InjectMocks
	private CustomAuthenticationEntryPoint entryPoint;

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private AuthenticationException authException;

	@Test
	void testCommence() throws Exception 
	{
		PrintWriter writer = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);
		entryPoint.commence(request, response, authException);
		verify(response).setContentType("application/json;charset=UTF-8");
		verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
	}
}
