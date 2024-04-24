package com.alexpages.ordermanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.alexpages.ordermanager.security.CustomAuthenticationEntryPoint;
import com.alexpages.ordermanager.security.JwtAuthFilter;
import com.alexpages.ordermanager.service.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class OrderManagerSecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;
	
    @Bean
    public UserDetailsService userDetailsService() 
    { 
        return new UserServiceImpl(); 
    } 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) 
    throws Exception 
    {
    	http.authenticationProvider(authenticationProvider()).addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(	management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(	handling -> handling.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));
        http.csrf			  ( csrf -> csrf.disable());
        http.headers		  ( headers -> headers.frameOptions().disable());
        
        http.authorizeHttpRequests()
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .antMatchers(HttpMethod.POST, "/users/request").permitAll()
            .antMatchers(HttpMethod.POST, "/users/authenticate").permitAll()
            .antMatchers(HttpMethod.GET, "/users/{userId}").permitAll()
            .antMatchers(HttpMethod.GET, "/orders/{orderId}").permitAll()
            .antMatchers(HttpMethod.POST, "/orders/request").permitAll()
            .antMatchers(HttpMethod.POST, "/orders/audit/request").permitAll()
            .antMatchers("/console/**").permitAll()
            .anyRequest().authenticated();

        return http.build();
    }
    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint()
    {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() 
    { 
        return new BCryptPasswordEncoder(); 
    } 
  
    @Bean
    public AuthenticationProvider authenticationProvider() 
    { 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
        authenticationProvider.setUserDetailsService(userDetailsService()); 
        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
        return authenticationProvider; 
    } 
  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
	throws Exception 
    { 
        return config.getAuthenticationManager(); 
    } 

}
