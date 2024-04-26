package com.alexpages.ordermanager.service;

import javax.validation.Valid;

import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.api.domain.UserInputData;
import com.alexpages.ordermanager.api.domain.UserOuputData;

public interface UserService 
{
	
    public String addUser(User user);

	public void deleteUserById(Long userId);

	public User getUserById(Long userId);

	public UserOuputData getUsers(@Valid UserInputData userInputData); 

}