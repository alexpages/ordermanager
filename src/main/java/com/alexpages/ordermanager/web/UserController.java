package com.alexpages.ordermanager.web;

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
import com.alexpages.ordermanager.api.domain.UserInputAudit1;
import com.alexpages.ordermanager.api.domain.UserInputData;
import com.alexpages.ordermanager.api.domain.UserOuputData;
import com.alexpages.ordermanager.api.domain.UserOutputAudit;
import com.alexpages.ordermanager.service.impl.JwtServiceImpl;
import com.alexpages.ordermanager.service.impl.UserServiceImpl;

import jakarta.validation.Valid;
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
	    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
	    } 
	}
	
	@Override
	public ResponseEntity<Void> deleteUserById(Long userId) {
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
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<UserOutputAudit> getUserAudit(@Valid UserInputAudit1 userInputAudit1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity<UserOuputData> getUserData(@Valid UserInputData userInputData) {
		// TODO Auto-generated method stub
		return null;
	}

}
