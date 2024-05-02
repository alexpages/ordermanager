package com.alexpages.ordermanager.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alexpages.ordermanager.error.OrderManagerException403;
import com.alexpages.ordermanager.service.impl.JwtServiceImpl;
import com.alexpages.ordermanager.service.impl.UserServiceImpl;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
    private JwtServiceImpl jwtServiceImpl; 
	@Autowired
    private UserServiceImpl userServiceImpl;
	
	@Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
        String authHeader = request.getHeader("Authorization"); 
        String token = null; 
        String username = null; 
        try {
        	if (authHeader != null && authHeader.startsWith("Bearer ")) 
        	{ 
                token = authHeader.substring(7); 
                username = jwtServiceImpl.extractUsername(token); 
            } 
        	
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
            { 
                UserDetails userDetails = userServiceImpl.loadUserByUsername(username); 
                if (Boolean.TRUE.equals(jwtServiceImpl.validateToken(token, userDetails))) 
                { 
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                    SecurityContextHolder.getContext().setAuthentication(authToken); 
                } 
            } 
            filterChain.doFilter(request, response);         
        
        } catch (Exception e) {
            String message = "There was an error validating the JWT: [" + e.getMessage() + "]";
            JSONObject jsonResponse = OrderManagerException403.constructJsonResponse(e, message);
        	
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        	response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonResponse.toString());
        }
    }
}
