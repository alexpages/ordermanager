package com.alexpages.ordermanager.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.alexpages.ordermanager.error.OrderManagerException403;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse response, AuthenticationException authException) 
	throws IOException, ServletException 
    {
        String message = "Access denied. Please ensure that your credentials are correct or that you have a valid token.";
        JSONObject jsonResponse = OrderManagerException403.constructJsonResponse(authException, message);
    	response.setContentType("application/json;charset=UTF-8");
    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(jsonResponse.toString());
    }
}

