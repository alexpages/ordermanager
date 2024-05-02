package com.alexpages.ordermanager.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.alexpages.ordermanager.api.UsersApi;
import com.alexpages.ordermanager.api.domain.AddUser201Response;
import com.alexpages.ordermanager.api.domain.AuthenticateRequest;
import com.alexpages.ordermanager.api.domain.AuthenticateResponse;
import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.api.domain.UserInputData;
import com.alexpages.ordermanager.api.domain.UserOuputData;
import com.alexpages.ordermanager.error.OrderManagerException403;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.service.impl.JwtServiceImpl;
import com.alexpages.ordermanager.service.impl.UserServiceImpl;
import com.alexpages.ordermanager.utils.ListUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserServiceImpl userService; 
    private final JwtServiceImpl jwtService; 
    private final AuthenticationManager authenticationManager; 

	@Override
	public ResponseEntity<AddUser201Response> addUser(@Valid User user) 
	{
		AddUser201Response response = new AddUser201Response();
		response.setUserId(userService.addUser(user));
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<AuthenticateResponse> authenticateUser(@Valid AuthenticateRequest authenticateRequest) 
	{
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	    		authenticateRequest.getUsername(), authenticateRequest.getPassword())); 
	    if (authentication.isAuthenticated()) { 
			AuthenticateResponse response = new AuthenticateResponse();
			response.setJwt(jwtService.generateToken(authenticateRequest.getUsername()));
	        return new ResponseEntity<>(response, HttpStatus.OK); 
	    } else {
	    	throw new OrderManagerException403("User could not authenticate correctly. Please review your credentials.");
	    } 
	}
	
	@Override
	public ResponseEntity<Void> deleteUserById(Long userId) 
	{
		userService.deleteUserById(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@Override
	public ResponseEntity<User> getUserById(Long userId) 
	{
		User user = userService.getUserById(userId);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			throw new OrderManagerException404("User with ID: [" + userId + "] was not found");
		}
	}

	@Override
	public ResponseEntity<UserOuputData> getUsers(@Valid UserInputData userInputData) 
	{
		UserOuputData response = userService.getUsers(userInputData);
		if (ListUtils.isBlank(response.getUsers())){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
}
