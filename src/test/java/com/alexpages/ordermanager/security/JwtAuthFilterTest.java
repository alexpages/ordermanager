package com.alexpages.ordermanager.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.alexpages.ordermanager.service.impl.JwtServiceImpl;
import com.alexpages.ordermanager.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {

	@InjectMocks
	private JwtAuthFilter filter;
	@Mock
	private JwtServiceImpl jwtServiceImpl;
	@Mock
	private UserServiceImpl userServiceImpl;
	
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private FilterChain filterChain;
	private UserDetails userDetails;
	
    @BeforeEach
    void setUp() 
    {
        httpServletRequest = mock(HttpServletRequest.class);
        httpServletResponse = mock(HttpServletResponse.class);
        userDetails = mock(UserDetails.class);
        filterChain = mock(FilterChain.class);
    }
    
    @Test
    void testDoFilter_success() throws ServletException, IOException 
    {
        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer VALID_TOKEN");
        when(jwtServiceImpl.validateToken(any(), any())).thenReturn(true);
        when(jwtServiceImpl.extractUsername("VALID_TOKEN")).thenReturn("username");
        mockSecurityContext();
        when(userServiceImpl.loadUserByUsername(any())).thenReturn(userDetails);
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
        when(userDetails.getAuthorities()).thenReturn((Collection) authorities);
        filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
    }
    
    @Test
    void testDoFilter_error() throws ServletException, IOException 
    {
        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer VALID_TOKEN");
        when(jwtServiceImpl.extractUsername("VALID_TOKEN")).thenReturn("username");
        mockSecurityContext();
        when(userServiceImpl.loadUserByUsername(any())).thenThrow(new RuntimeException("some error"));
        when(httpServletResponse.getWriter()).thenReturn(new PrintWriter("error"));
        filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
    }
    
	private void mockSecurityContext() 
	{
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(null);
	}

}